package dev.vengateshm.compose_samples.maps_spot_finder.domain.model

data class ParkingSpot(
    val lat: Double,
    val lng: Double,
    val id: Int? = null,
)
