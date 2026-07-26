package com.rideassistant.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Model pentru datele unei curse
 */
@Entity(tableName = "rides")
data class Ride(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    // Informații de bază
    val platform: String, // "BOLT" sau "UBER"
    val price: Double,
    val distance: Double,
    val estimatedTime: Int, // în minute
    
    // Locații
    val pickupLocation: String,
    val dropoffLocation: String,
    val pickupLat: Double = 0.0,
    val pickupLng: Double = 0.0,
    val dropoffLat: Double = 0.0,
    val dropoffLng: Double = 0.0,
    
    // Calcule profit
    val fuelCost: Double = 0.0,
    val commission: Double = 0.0,
    val grossProfit: Double = 0.0,
    val netProfit: Double = 0.0,
    val ronPerKm: Double = 0.0,
    val ronPerHour: Double = 0.0,
    val profitScore: Int = 0, // 0-100
    
    // Timestamp
    val createdAt: Long = System.currentTimeMillis(),
    val acceptedAt: Long = 0,
    val completedAt: Long = 0,
    
    // Status
    val status: String = "PENDING", // PENDING, ACCEPTED, COMPLETED, REJECTED
    val notes: String = ""
) : Serializable
