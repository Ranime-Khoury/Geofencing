package com.example.geofencing.repository

import com.example.geofencing.data.AppDatabase
import com.example.geofencing.data.dao.LogDao
import com.example.geofencing.data.model.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppRepository @Inject constructor(private val appDatabase: AppDatabase) {
    private val logDao: LogDao = appDatabase.getLogDao()

    val logs: Flow<List<Log>> = logDao.getAllLogs()

    suspend fun insertLog(log: Log) = withContext(Dispatchers.IO) {
        logDao.insertLog(log)
    }
}
