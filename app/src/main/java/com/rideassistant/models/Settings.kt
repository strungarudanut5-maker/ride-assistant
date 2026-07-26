package com.rideassistant.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Model pentru setări aplicație
 */
@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey
    val id: Int = 1,
    
    // Parametri mașină
    val fuelConsumptionPer100km: Double = 8.0, // l/100km
    val fuelPrice: Double = 6.0, // RON/l
    
    // Comisioane
    val boltCommissionPercent: Double = 20.0,
    val uberCommissionPercent: Double = 25.0,
    
    // Praguri acceptare
    val minimumProfit: Double = 10.0, // RON
    val minimumRonPerKm: Double = 5.0, // RON/km
    
    // Overlay
    val overlayWidth: Int = 250,
    val overlayHeight: Int = 200,
    val overlayX: Int = 0,
    val overlayY: Int = 0,
    val overlayOpacity: Float = 0.9f,
    
    // Comportament
    val autoStart: Boolean = true,
    val darkMode: Boolean = false,
    val vibrationEnabled: Boolean = true,
    val soundNotifications: Boolean = true,
    val acceptableProfit: Double = 15.0,
    val goodProfit: Double = 25.0
)
