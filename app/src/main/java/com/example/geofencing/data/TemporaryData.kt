package com.example.geofencing.data

import co.anbora.labs.spatia.geometry.LineString
import co.anbora.labs.spatia.geometry.Point
import co.anbora.labs.spatia.geometry.Polygon
import com.example.geofencing.data.model.DevicePositions
import com.example.geofencing.data.model.Location

val dataDevicePositions = listOf(
    DevicePositions(
        id = 1,
        deviceId = 1,
        timestamp = "2023-09-27T12:00:00",
        position = Point(15.0, 15.0) // Inside Location 1
    ),
    DevicePositions(
        id = 2,
        deviceId = 1,
        timestamp = "2023-09-27T12:00:05",
        position = Point(17.0, 17.0) // Near the edge of Location 1
    ),
    DevicePositions(
        id = 3,
        deviceId = 1,
        timestamp = "2023-09-27T12:00:10",
        position = Point(20.0, 10.0) // Exiting Location 1
    ),
    DevicePositions(
        id = 4,
        deviceId = 1,
        timestamp = "2023-09-27T12:00:15",
        position = Point(30.0, 30.0) // Inside Location 2
    ),
    DevicePositions(
        id = 5,
        deviceId = 1,
        timestamp = "2023-09-27T12:00:20",
        position = Point(35.0, 35.0) // Near the edge of Location 2
    ),
    DevicePositions(
        id = 6,
        deviceId = 1,
        timestamp = "2023-09-27T12:00:25",
        position = Point(40.0, 40.0) // Exiting Location 2
    ),
    DevicePositions(
        id = 7,
        deviceId = 1,
        timestamp = "2023-09-27T12:00:30",
        position = Point(55.0, 52.0) // Inside Location 3
    ),
    DevicePositions(
        id = 8,
        deviceId = 1,
        timestamp = "2023-09-27T12:00:35",
        position = Point(57.0, 55.0) // Near the edge of Location 3
    ),
    DevicePositions(
        id = 10,
        deviceId = 1,
        timestamp = "2023-09-27T12:00:45",
        position = Point(53.0, 54.0) // Inside Location 3
    ),
    DevicePositions(
        id = 11,
        deviceId = 1,
        timestamp = "2023-09-27T12:00:50",
        position = Point(59.0, 57.0) // Near the edge of Location 3
    ),
    DevicePositions(
        id = 12,
        deviceId = 1,
        timestamp = "2023-09-27T12:00:55",
        position = Point(61.0, 61.0) // Exiting Location 3
    )
)

val dataLocations = listOf(
    Location(
        id = 1,
        name = "Coastal Highlands",
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
        name = "Mount Cascade Range",
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
        name = "Serene Valley",
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