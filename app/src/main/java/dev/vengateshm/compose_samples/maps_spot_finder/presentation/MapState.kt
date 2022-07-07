package dev.vengateshm.compose_samples.maps_spot_finder.presentation

import com.google.maps.android.compose.MapProperties
import dev.vengateshm.compose_samples.maps_spot_finder.domain.model.ParkingSpot

data class MapState(
    val properties: MapProperties = MapProperties(),
    val parkingSpots: List<ParkingSpot> = emptyList(),
    val isFalloutMap: Boolean = false,
)
