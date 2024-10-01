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
                db.query("SELECT InitSpatialMetaData();").moveToNext()

                db.query("SELECT RecoverGeometryColumn('device_positions', 'position', 4326, 'POINT', 'XY');")
                    .moveToNext()
                db.query("SELECT CreateSpatialIndex('device_positions', 'position');").moveToNext()

                db.query("SELECT RecoverGeometryColumn('location', 'polygon', 4326, 'POLYGON', 'XY');")
                    .moveToNext()
                db.query("SELECT CreateSpatialIndex('location', 'polygon');").moveToNext()
            }
        }).build()
    }
}
