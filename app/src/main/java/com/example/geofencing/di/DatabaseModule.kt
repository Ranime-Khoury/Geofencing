package com.example.geofencing.di

import android.app.Application
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import co.anbora.labs.spatia.builder.SpatiaRoom
import com.example.geofencing.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application): AppDatabase {
        return SpatiaRoom.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                // Initialize Spatialite
                db.query("SELECT InitSpatialMetaData();").moveToNext()
                // Recover geometry column and create a spatial index
//                db.query("SELECT RecoverGeometryColumn('geo_posts', 'location', 4326, 'POINT', 'XY');")
//                    .moveToNext()
//                db.query("SELECT CreateSpatialIndex('geo_posts', 'location');")
//                    .moveToNext()
            }
        }).build()
    }
}
