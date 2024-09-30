package com.example.geofencing.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.geofencing.data.model.DevicePositions
import com.example.geofencing.data.model.Location
import com.example.geofencing.data.model.Log
import kotlinx.coroutines.flow.Flow

@Dao
interface LogDao {

    //devicePositions
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevicePosition(devicePosition: DevicePositions)


    // Location
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)

    @Query("SELECT * FROM ${Location.TABLE_NAME} WHERE id = :id")
    suspend fun getLocationById(id: Int): Location


    // Log
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: Log)

    @Query(
        """
        UPDATE ${Log.TABLE_NAME} 
        SET exitTime = :newExitTime 
        WHERE deviceId = :deviceId AND exitTime IS NULL
    """
    )
    suspend fun updateLog(deviceId: Int, newExitTime: String)

    @Query("SELECT * FROM ${Log.TABLE_NAME} WHERE exitTime IS NULL ORDER BY id DESC LIMIT 1")
    suspend fun getUnfinishedLog(): Log?

    @Delete
    suspend fun deleteLog(log: Log)

    @Query("SELECT * FROM ${Log.TABLE_NAME} ORDER BY id DESC")
    fun getAllLogs(): Flow<List<Log>>
}
