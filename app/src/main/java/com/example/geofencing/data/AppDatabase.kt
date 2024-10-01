package com.example.geofencing.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import co.anbora.labs.spatia.geometry.GeometryConverters
import com.example.geofencing.data.dao.LogDao
import com.example.geofencing.data.model.DevicePositions
import com.example.geofencing.data.model.Location
import com.example.geofencing.data.model.Log


@Database(entities = [Location::class, DevicePositions::class, Log::class], version = 1)
@TypeConverters(GeometryConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getLogDao(): LogDao

    companion object {
        const val DB_NAME = "geofencing_database"
    }
}
