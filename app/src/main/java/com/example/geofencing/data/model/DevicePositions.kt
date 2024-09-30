package com.example.geofencing.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import co.anbora.labs.spatia.geometry.Point
import com.example.geofencing.data.model.DevicePositions.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class DevicePositions(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var deviceId: Int,
    var timestamp: String,
    var position: Point
) {
    companion object {
        const val TABLE_NAME = "device_positions"
    }
}
