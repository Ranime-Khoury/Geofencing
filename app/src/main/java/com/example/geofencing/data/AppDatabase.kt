package com.example.geofencing.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import co.anbora.labs.spatia.geometry.GeometryConverters
import com.example.geofencing.data.model.Area
import com.example.geofencing.data.model.Log
import com.example.geofencing.data.model.Position


@Database(entities = [Area::class, Position::class, Log::class], version = 1)
@TypeConverters(GeometryConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getLogDao(): LogDao

    companion object {
        const val DB_NAME = "geofencing_database"
    }
}
