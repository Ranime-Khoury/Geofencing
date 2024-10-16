package com.example.geofencing.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.SkipQueryVerification
import co.anbora.labs.spatia.geometry.Point
import co.anbora.labs.spatia.geometry.Polygon
import com.example.geofencing.data.model.Area
import com.example.geofencing.data.model.Log
import com.example.geofencing.data.model.Position
import kotlinx.coroutines.flow.Flow

@Dao
interface LogDao {

    //Position
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosition(position: Position)


    // Area
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArea(area: Area)

    @Query("SELECT * FROM ${Area.TABLE_NAME} WHERE id = :id")
    suspend fun getAreaById(id: Int): Area


    // Log
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: Log)

    @Query(
        """
        UPDATE ${Log.TABLE_NAME} 
        SET exitTime = :newExitTime 
        WHERE exitTime IS NULL
    """
    )
    suspend fun updateLog(newExitTime: String)

    @Query("SELECT * FROM ${Log.TABLE_NAME} WHERE exitTime IS NULL ORDER BY id DESC LIMIT 1")
    suspend fun getUnfinishedLog(): Log?

    @Delete
    suspend fun deleteLog(log: Log)

    @Query("SELECT * FROM ${Log.TABLE_NAME} ORDER BY id DESC")
    fun getAllLogs(): Flow<List<Log>>


    // Geometry
    @Query("SELECT ST_Within(:point, :polygon)")
    @SkipQueryVerification
    suspend fun isPointWithinPolygon(point: Point, polygon: Polygon): Boolean
    @Query(
        """
        SELECT *
        FROM ${Area.TABLE_NAME} 
        WHERE ST_Within(:point, polygon)
        LIMIT 1
    """
    )
    @SkipQueryVerification
    fun findContainingArea(point: Point): Area?
}
