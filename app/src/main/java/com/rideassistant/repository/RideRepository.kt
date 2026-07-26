package com.rideassistant.repository

import com.rideassistant.database.RideDao
import com.rideassistant.database.SettingsDao
import com.rideassistant.models.Ride
import com.rideassistant.models.Settings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository pentru gestiunea datelor curselor
 */
@Singleton
class RideRepository @Inject constructor(
    private val rideDao: RideDao,
    private val settingsDao: SettingsDao
) {
    // Ride operations
    suspend fun insertRide(ride: Ride): Long = rideDao.insertRide(ride)

    suspend fun updateRide(ride: Ride) = rideDao.updateRide(ride)

    suspend fun deleteRide(ride: Ride) = rideDao.deleteRide(ride)

    fun getAllRides(): Flow<List<Ride>> = rideDao.getAllRides()

    fun getRidesByStatus(status: String): Flow<List<Ride>> = rideDao.getRidesByStatus(status)

    fun getRidesByPlatform(platform: String): Flow<List<Ride>> = rideDao.getRidesByPlatform(platform)

    fun getRidesBetweenDates(startTime: Long, endTime: Long): Flow<List<Ride>> =
        rideDao.getRidesBetweenDates(startTime, endTime)

    fun getRideCount(): Flow<Long> = rideDao.getRideCount()

    fun getTotalProfit(startTime: Long): Flow<Double?> = rideDao.getTotalProfit(startTime)

    fun getAverageRonPerKm(): Flow<Double?> = rideDao.getAverageRonPerKm()

    suspend fun deleteOldRides(timestamp: Long) = rideDao.deleteOldRides(timestamp)

    // Settings operations
    suspend fun insertSettings(settings: Settings) = settingsDao.insertSettings(settings)

    suspend fun updateSettings(settings: Settings) = settingsDao.updateSettings(settings)

    fun getSettings(): Flow<Settings?> = settingsDao.getSettings()

    suspend fun getSettingsDirect(): Settings? = settingsDao.getSettingsDirect()
}
