package com.example.geofencing.repository

import KalmanFilter
import co.anbora.labs.spatia.geometry.Point
import com.example.geofencing.data.AppDatabase
import com.example.geofencing.data.allAreas
import com.example.geofencing.data.dao.LogDao
import com.example.geofencing.data.model.Area
import com.example.geofencing.data.model.Log
import com.example.geofencing.data.model.Position
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

const val LIMIT_DURATION: Long = 15000 // in milliseconds

@Singleton
class AppRepository @Inject constructor(private val appDatabase: AppDatabase) {
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

                    dao.deleteLog(
                        Log(
                            areaId = it.id,
                            areaName = it.name,
                            entryTime = lastEntryTime,
                            exitTime = null
                        )
                    )
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
}
