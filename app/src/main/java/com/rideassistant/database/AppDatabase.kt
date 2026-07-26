package com.rideassistant.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rideassistant.models.Ride
import com.rideassistant.models.Settings

/**
 * Room Database pentru aplicație
 */
@Database(
    entities = [Ride::class, Settings::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rideDao(): RideDao
    abstract fun settingsDao(): SettingsDao
}
