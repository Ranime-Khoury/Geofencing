package com.example.geofencing.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import co.anbora.labs.spatia.geometry.Point
import com.example.geofencing.data.model.Position.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Position(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var position: Point,
    var timestamp: String
) {
    companion object {
        const val TABLE_NAME = "position"
    }
}
