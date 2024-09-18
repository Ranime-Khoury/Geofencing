package com.example.geofencing.data.model

import androidx.room.Entity
import co.anbora.labs.spatia.geometry.Point
import com.example.geofencing.data.model.Device.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME, primaryKeys = ["id", "timestamp"])
data class Device(
    var id: Int,
    var timestamp: String,
    var position: Point
) {
    companion object {
        const val TABLE_NAME = "device"
    }
}
