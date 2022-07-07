package dev.vengateshm.compose_samples.maps_spot_finder.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ParkingSpotEntity::class],
    version = 1
)
abstract class ParkingSpotDatabase : RoomDatabase() {
    abstract val parkingSpotDao: ParkingSpotDao
}