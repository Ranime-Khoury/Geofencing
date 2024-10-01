package com.example.geofencing.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.geofencing.data.model.Log.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Log(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var deviceId: Int,
    var locationId: Int,
    var locationName: String,
    var entryTime: String,
    var exitTime: String?
) {
    companion object {
        const val TABLE_NAME = "log"
    }
}
