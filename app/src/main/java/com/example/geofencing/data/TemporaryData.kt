package com.example.geofencing.data

import co.anbora.labs.spatia.geometry.LineString
import co.anbora.labs.spatia.geometry.Point
import co.anbora.labs.spatia.geometry.Polygon
import com.example.geofencing.data.model.DevicePositions
import com.example.geofencing.data.model.Location

val dataDevicePositions = listOf(
    DevicePositions(
        id = 1,
        device_id = 1,
        timestamp = "2023-09-27T12:00:00",
        position = Point(15.0, 15.0) // Inside Location 1
    ),
    DevicePositions(
        id = 2,
        device_id = 1,
        timestamp = "2023-09-27T12:00:05",
        position = Point(17.0, 17.0) // Near the edge of Location 1
    ),
    DevicePositions(
        id = 3,
        device_id = 1,
        timestamp = "2023-09-27T12:00:10",
        position = Point(20.0, 10.0) // Exiting Location 1
    ),
    DevicePositions(
        id = 4,
        device_id = 1,
        timestamp = "2023-09-27T12:00:15",
        position = Point(30.0, 30.0) // Inside Location 2
    ),
    DevicePositions(
        id = 5,
        device_id = 1,
        timestamp = "2023-09-27T12:00:20",
        position = Point(35.0, 35.0) // Near the edge of Location 2
    ),
    DevicePositions(
        id = 6,
        device_id = 1,
        timestamp = "2023-09-27T12:00:25",
        position = Point(40.0, 40.0) // Exiting Location 2
    ),
    DevicePositions(
        id = 7,
        device_id = 1,
        timestamp = "2023-09-27T12:00:30",
        position = Point(55.0, 52.0) // Inside Location 3
    ),
    DevicePositions(
        id = 8,
        device_id = 1,
        timestamp = "2023-09-27T12:00:35",
        position = Point(57.0, 55.0) // Near the edge of Location 3
    ),
    DevicePositions(
        id = 9,
        device_id = 1,
        timestamp = "2023-09-27T12:00:40",
        position = Point(60.0, 60.0) // Exiting Location 3
    )
)

val dataLocations = listOf(
    Location(
        id = 1,
        name = "Location 1",
        polygon = Polygon(
            listOf(
                LineString(
                    listOf(
                        Point(10.0, 10.0, 4326),
                        Point(15.0, 5.0, 4326),
                        Point(20.0, 10.0, 4326),
                        Point(22.0, 15.0, 4326),
                        Point(20.0, 20.0, 4326),
                        Point(15.0, 20.0, 4326),
                        Point(10.0, 15.0, 4326),
                        Point(10.0, 10.0, 4326) // Closing the polygon
                    )
                )
            )
        )
    ),
    Location(
        id = 2,
        name = "Location 2",
        polygon = Polygon(
            listOf(
                LineString(
                    listOf(
                        Point(30.0, 30.0, 4326),
                        Point(35.0, 32.0, 4326),
                        Point(38.0, 35.0, 4326),
                        Point(40.0, 40.0, 4326),
                        Point(38.0, 42.0, 4326),
                        Point(35.0, 40.0, 4326),
                        Point(32.0, 38.0, 4326),
                        Point(30.0, 30.0, 4326) // Closing the polygon
                    )
                )
            )
        )
    ),
    Location(
        id = 3,
        name = "Location 3",
        polygon = Polygon(
            listOf(
                LineString(
                    listOf(
                        Point(50.0, 50.0, 4326),
                        Point(55.0, 52.0, 4326),
                        Point(58.0, 55.0, 4326),
                        Point(60.0, 60.0, 4326),
                        Point(57.0, 58.0, 4326),
                        Point(54.0, 57.0, 4326),
                        Point(51.0, 55.0, 4326),
                        Point(50.0, 50.0, 4326) // Closing the polygon
                    )
                )
            )
        )
    )
)