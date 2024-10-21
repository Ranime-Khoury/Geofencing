package com.example.geofencing.data

import co.anbora.labs.spatia.geometry.LineString
import co.anbora.labs.spatia.geometry.Point
import co.anbora.labs.spatia.geometry.Polygon
import com.example.geofencing.data.model.Area

val allAreas = listOf(
    Area(
        id = 1, name = "Building 1", polygon = Polygon(
            listOf(
                LineString(
                    listOf(
                        Point(33.9295263, 35.6233812, 4326),
                        Point(33.9296512, 35.6234271, 4326),
                        Point(33.9296061, 35.6236135, 4326),
                        Point(33.9294782, 35.6235686, 4326),
                        Point(33.9295263, 35.6233812, 4326)
                    )
                )
            )
        )
    ),
    Area(
        id = 2, name = "Building 2", polygon = Polygon(
            listOf(
                LineString(
                    listOf(
                        Point(33.9294078, 35.6232883, 4326),
                        Point(33.9294976, 35.6233275, 4326),
                        Point(33.9294331, 35.6235280, 4326),
                        Point(33.9293418, 35.6234841, 4326),
                        Point(33.9294078, 35.6232883, 4326)
                    )
                )
            )
        )
    ),
    Area(
        id = 3, name = "Building 3", polygon = Polygon(
            listOf(
                LineString(
                    listOf(
                        Point(33.9293566, 35.6229926, 4326),
                        Point(33.9295338, 35.6230248, 4326),
                        Point(33.9295177, 35.6231720, 4326),
                        Point(33.9293388, 35.6231381, 4326),
                        Point(33.9293566, 35.6229926, 4326)
                    )
                )
            )
        )
    ),
    Area(
        id = 4, name = "Building 4", polygon = Polygon(
            listOf(
                LineString(
                    listOf(
                        Point(33.9295989, 35.6230486, 4326),
                        Point(33.9297305, 35.6230627, 4326),
                        Point(33.9297205, 35.6232665, 4326),
                        Point(33.9295791, 35.6232528, 4326),
                        Point(33.9295989, 35.6230486, 4326)
                    )
                )
            )
        )
    ),
    Area(
        id = 5, name = "Library", polygon = Polygon(
            listOf(
                LineString(
                    listOf(
                        Point(34.116336, 35.674138, 4326),
                        Point(34.116363, 35.674069, 4326),
                        Point(34.116462, 35.674039, 4326),
                        Point(34.116521, 35.674087, 4326),
                        Point(34.116539, 35.674186, 4326),
                        Point(34.116454, 35.674275, 4326),
                        Point(34.116410, 35.674288, 4326),
                        Point(34.116356, 35.674248, 4326),
                        Point(34.116371, 35.674373, 4326),
                        Point(34.116165, 35.674417, 4326),
                        Point(34.116155, 35.674354, 4326),
                        Point(34.116075, 35.674370, 4326),
                        Point(34.116049, 35.674198, 4326),
                        Point(34.116336, 35.674138, 4326)
                    )
                )
            )
        )
    ),
    Area(
        id = 6, name = "Block A", polygon = Polygon(
            listOf(
                LineString(
                    listOf(
                        Point(34.116171, 35.673508),
                        Point(34.116336, 35.673254),
                        Point(34.116707, 35.673561),
                        Point(34.116520, 35.673910),
                        Point(34.116171, 35.673508),
                    )
                )
            )
        )
    )
)