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

const val MINIMUM_DURATION: Long = 4000 // in milliseconds

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
                if (newPosition.timestamp - lastEntryTime < MINIMUM_DURATION) {
                    dao.deleteLog(
                        Log(
                            areaId = it.id,
                            areaName = it.name,
                            entryTime = lastEntryTime,
                            exitTime = null
                        )
                    )
                } else {
                    dao.updateLog(newPosition.timestamp)
                }
                previousArea = null
                handleNewPosition(newPosition)
            }
        } ?: run {
            previousArea =
                dao.findContainingArea(newPosition.point)
            android.util.Log.i("mytag", "1")

            previousArea?.let {
                lastEntryTime = newPosition.timestamp
                dao.insertLog(
                    Log(
                        areaId = it.id,
                        areaName = it.name,
                        entryTime = lastEntryTime,
                        exitTime = null
                    )
                )
            }
        }
    }
}
