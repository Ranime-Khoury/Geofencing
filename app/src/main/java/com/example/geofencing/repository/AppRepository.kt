package com.example.geofencing.repository

import com.example.geofencing.data.AppDatabase
import com.example.geofencing.data.dao.LogDao
import com.example.geofencing.data.dataDevicePositions
import com.example.geofencing.data.dataLocations
import com.example.geofencing.data.model.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppRepository @Inject constructor(private val appDatabase: AppDatabase) {
    private val dao: LogDao = appDatabase.getLogDao()

    val logs: Flow<List<Log>> = dao.getAllLogs()

    suspend fun insertAllDataIntoDB() {
        for (location in dataLocations) {
            dao.insertLocation(location)
        }
        for (devicePosition in dataDevicePositions) {
            dao.insertDevicePosition(devicePosition)
            delay(5000)
        }
    }
}
