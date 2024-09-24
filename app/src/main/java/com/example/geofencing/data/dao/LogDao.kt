package com.example.geofencing.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.geofencing.data.model.DevicePositions
import com.example.geofencing.data.model.Location
import com.example.geofencing.data.model.Log
import kotlinx.coroutines.flow.Flow

@Dao
interface LogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: Log)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevicePosition(devicePosition: DevicePositions)

    @Query("SELECT * FROM ${Log.TABLE_NAME}")
    fun getAllLogs(): Flow<List<Log>>
}
