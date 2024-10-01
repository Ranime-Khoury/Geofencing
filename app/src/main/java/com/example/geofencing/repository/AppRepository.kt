package com.example.geofencing.repository

import KalmanFilter
import co.anbora.labs.spatia.geometry.Point
import com.example.geofencing.data.AppDatabase
import com.example.geofencing.data.dao.LogDao
import com.example.geofencing.data.dataDevicePositions
import com.example.geofencing.data.dataLocations
import com.example.geofencing.data.model.DevicePositions
import com.example.geofencing.data.model.Location
import com.example.geofencing.data.model.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

const val MINIMUM_DURATION: Long = 4 // in seconds

@Singleton
class AppRepository @Inject constructor(private val appDatabase: AppDatabase) {
    private val dao: LogDao = appDatabase.getLogDao()
    val logs: Flow<List<Log>> = dao.getAllLogs()

    private var previousLocation: Location? = null
    private var lastEntryTime = "0000-01-01T00:00:00"

    private val kalmanFilter = KalmanFilter()

    init {
        GlobalScope.launch(Dispatchers.IO) {
            val unfinishedLog: Log? = dao.getUnfinishedLog()
            unfinishedLog?.let {
                previousLocation = dao.getLocationById(unfinishedLog.locationId)
                lastEntryTime = unfinishedLog.entryTime
            }
        }
    }

    suspend fun insertAllDataIntoDB() {
        for (location in dataLocations) {
            dao.insertLocation(location)
        }
        for (devicePosition in dataDevicePositions) {
            dao.insertDevicePosition(devicePosition)
            handleNewPosition(devicePosition)
            delay(5000)
        }
    }

    private suspend fun handleNewPosition(currentPosition: DevicePositions) {
        val measurement = doubleArrayOf(currentPosition.position.x, currentPosition.position.y)
        kalmanFilter.predict()
        kalmanFilter.update(measurement)

        val estimatedPosition = kalmanFilter.getCurrentEstimate()
        currentPosition.position = Point(estimatedPosition[0], estimatedPosition[1])

        previousLocation?.let {
            if (
                !dao.isPointWithinPolygon(
                    currentPosition.position.x,
                    currentPosition.position.y,
                    it.polygon
                )
            ) {
                if (!isDurationSufficient(
                        lastEntryTime,
                        currentPosition.timestamp
                    )
                ) {
                    dao.deleteLog(
                        Log(
                            deviceId = currentPosition.deviceId,
                            locationId = it.id,
                            locationName = it.name,
                            entryTime = lastEntryTime,
                            exitTime = null
                        )
                    )
                } else {
                    dao.updateLog(currentPosition.deviceId, currentPosition.timestamp)
                }
                previousLocation = null
                handleNewPosition(currentPosition)
            }
        } ?: run {
            previousLocation =
                dao.findContainingLocation(currentPosition.position.x, currentPosition.position.y)
            previousLocation?.let {
                lastEntryTime = currentPosition.timestamp
                dao.insertLog(
                    Log(
                        deviceId = currentPosition.deviceId,
                        locationId = it.id,
                        locationName = it.name,
                        entryTime = lastEntryTime,
                        exitTime = null
                    )
                )
            }
        }
    }

    private fun isDurationSufficient(entryTime: String, exitTime: String): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

        val entryDateTime = LocalDateTime.parse(entryTime, formatter)
        val exitDateTime = LocalDateTime.parse(exitTime, formatter)

        val duration = Duration.between(entryDateTime, exitDateTime)

        return duration.seconds >= MINIMUM_DURATION
    }
}
