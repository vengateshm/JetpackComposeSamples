package dev.vengateshm.compose_samples.maps_spot_finder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parking_spot")
data class ParkingSpotEntity(
    val lat: Double,
    val lng: Double,
    @PrimaryKey
    val id: Int? = null,
)
