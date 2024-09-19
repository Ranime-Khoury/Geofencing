package com.example.geofencing.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import co.anbora.labs.spatia.geometry.Polygon

import com.example.geofencing.data.model.Location.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Location(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String,
    var polygon: Polygon
) {
    companion object {
        const val TABLE_NAME = "location"
    }
}
