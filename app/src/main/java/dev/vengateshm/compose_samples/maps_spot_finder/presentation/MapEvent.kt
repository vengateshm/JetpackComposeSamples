package dev.vengateshm.compose_samples.maps_spot_finder.presentation

import com.google.android.gms.maps.model.LatLng
import dev.vengateshm.compose_samples.maps_spot_finder.domain.model.ParkingSpot

sealed class MapEvent {
    object ToggleFalloutMap : MapEvent()
    data class OnMapLongClick(val latLng: LatLng) : MapEvent()
    data class OnInfoWindowLongClick(val spot: ParkingSpot) : MapEvent()
}