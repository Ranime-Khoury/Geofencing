package com.example.geofencing.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import co.anbora.labs.spatia.geometry.Polygon
import com.example.geofencing.data.model.Area.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Area(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String,
    var polygon: Polygon
) {
    companion object {
        const val TABLE_NAME = "area"
    }
}
