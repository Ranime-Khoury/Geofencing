package com.example.geofencing.repository

import KalmanFilter
import android.content.Context
import co.anbora.labs.spatia.geometry.Point
import com.example.geofencing.data.AppDatabase
import com.example.geofencing.data.LogDao
import com.example.geofencing.data.allAreas
import com.example.geofencing.data.model.Area
import com.example.geofencing.data.model.Log
import com.example.geofencing.data.model.Position
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

const val LIMIT_DURATION: Long = 15000 // in milliseconds

@Singleton
class AppRepository @Inject constructor(
    private val appDatabase: AppDatabase,
    @ApplicationContext private val application: Context
) {
    private val dao: LogDao = appDatabase.getLogDao()
    val logs: Flow<List<Log>> = dao.getAllLogs()

    private val _newPosition = MutableStateFlow<String?>(null)
    val newPosition: StateFlow<String?> get() = _newPosition

    private var previousArea: Area? = null
    private var lastEntryTime = 0L

    private val kalmanFilter = KalmanFilter()

    init {
        GlobalScope.launch(Dispatchers.IO) {
            val unfinishedLog: Log? = dao.getUnfinishedLog()
            unfinishedLog?.let {
                previousArea = dao.getAreaById(unfinishedLog.areaId)
                lastEntryTime = unfinishedLog.entryTime
            }
        }
    }

    suspend fun insertAreasIntoDB() {
        for (area in allAreas) {
            dao.insertArea(area)
        }
    }

    suspend fun handleNewPosition(newPosition: Position) {
        _newPosition.value = "(${newPosition.point.x}, ${newPosition.point.y})"

        dao.insertPosition(newPosition)

        val measurement = doubleArrayOf(newPosition.point.x, newPosition.point.y)
        kalmanFilter.predict()
        kalmanFilter.update(measurement)

        val estimatedPosition = kalmanFilter.getCurrentEstimate()
        newPosition.point = Point(estimatedPosition[0], estimatedPosition[1])

        previousArea?.let {
            if (
                !dao.isPointWithinPolygon(
                    newPosition.point,
                    it.polygon
                )
            ) {
                if (newPosition.timestamp - lastEntryTime < LIMIT_DURATION) {
                    android.util.Log.i("mytag", "deleted log")

                    dao.deleteLog(lastEntryTime)
                } else {
                    android.util.Log.i("mytag", "updated log")

                    dao.updateLog(newPosition.timestamp)
                }
                previousArea = null
                handleNewPosition(newPosition)
            }
        } ?: run {
            android.util.Log.i("mytag", "entered insert phase")

            previousArea =
                dao.findContainingArea(newPosition.point)
            android.util.Log.i("mytag", if (previousArea != null) "in" else "out")

            previousArea?.let {
                lastEntryTime = newPosition.timestamp
                android.util.Log.i("mytag", "will insert log")
                dao.insertLog(
                    Log(
                        areaId = it.id,
                        areaName = it.name,
                        entryTime = lastEntryTime,
                        exitTime = null
                    )
                )
                android.util.Log.i("mytag", "log successfully inserted")
            } ?: {
                android.util.Log.i("mytag", "no area")

            }
        }
    }

    suspend fun logDatabaseEntries() {
        val entries = dao.getAllEntries()

        val logData = StringBuilder()
        for (entry in entries) {
            logData.append(
                "id: ${entry.id}, areaId: ${entry.areaId}, areaName: ${entry.areaName}, entryTime: ${
                    SimpleDateFormat(
                        "dd MMM yyyy HH:mm:ss",
                        Locale.getDefault()
                    ).format(Date(entry.entryTime))
                }, exitTime: ${
                    entry.exitTime?.let {
                        SimpleDateFormat(
                            "dd MMM yyyy HH:mm:ss",
                            Locale.getDefault()
                        ).format(Date(it)) ?: "Still inside the area"
                    }
                }\n"
            )
        }
        writeLogToFile(logData.toString())
    }

    private fun writeLogToFile(logData: String) {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val logFile = File(application.getExternalFilesDir(null), "database_log_$timestamp.log")
        try {
            FileWriter(logFile, true).use { writer ->
                writer.append(logData)
                writer.flush()
            }
        } catch (e: IOException) {
            e.printStackTrace() // Handle exceptions
        }
    }
}
