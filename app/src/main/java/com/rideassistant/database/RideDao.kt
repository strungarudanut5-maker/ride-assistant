package com.rideassistant.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rideassistant.models.Ride
import kotlinx.coroutines.flow.Flow

/**
 * DAO pentru operații cu cursele
 */
@Dao
interface RideDao {
    @Insert
    suspend fun insertRide(ride: Ride): Long

    @Update
    suspend fun updateRide(ride: Ride)

    @Delete
    suspend fun deleteRide(ride: Ride)

    @Query("SELECT * FROM rides WHERE id = :id")
    suspend fun getRideById(id: Long): Ride?

    @Query("SELECT * FROM rides ORDER BY createdAt DESC")
    fun getAllRides(): Flow<List<Ride>>

    @Query("SELECT * FROM rides WHERE status = :status ORDER BY createdAt DESC")
    fun getRidesByStatus(status: String): Flow<List<Ride>>

    @Query("SELECT * FROM rides WHERE platform = :platform ORDER BY createdAt DESC")
    fun getRidesByPlatform(platform: String): Flow<List<Ride>>

    @Query("SELECT * FROM rides WHERE createdAt >= :startTime AND createdAt <= :endTime ORDER BY createdAt DESC")
    fun getRidesBetweenDates(startTime: Long, endTime: Long): Flow<List<Ride>>

    @Query("SELECT COUNT(*) FROM rides")
    fun getRideCount(): Flow<Long>

    @Query("SELECT SUM(netProfit) FROM rides WHERE createdAt >= :startTime")
    fun getTotalProfit(startTime: Long): Flow<Double?>

    @Query("SELECT AVG(ronPerKm) FROM rides")
    fun getAverageRonPerKm(): Flow<Double?>

    @Query("DELETE FROM rides WHERE createdAt < :timestamp")
    suspend fun deleteOldRides(timestamp: Long)
}
