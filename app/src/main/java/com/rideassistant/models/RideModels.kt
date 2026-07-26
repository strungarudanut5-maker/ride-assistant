package com.rideassistant.models

/**
 * Model pentru datele extrase prin OCR
 */
data class RideData(
    val platform: String? = null,
    val price: Double? = null,
    val distance: Double? = null,
    val estimatedTime: Int? = null,
    val pickupLocation: String? = null,
    val dropoffLocation: String? = null,
    val extraInfo: String? = null,
    val confidence: Float = 0f,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Model pentru rezultatul calculului de profit
 */
data class ProfitResult(
    val grossProfit: Double,
    val fuelCost: Double,
    val commission: Double,
    val netProfit: Double,
    val ronPerKm: Double,
    val ronPerHour: Double,
    val profitScore: Int, // 0-100
    val profitLevel: ProfitLevel
)

enum class ProfitLevel {
    EXCELLENT,  // 🟢 Verde - > 25 RON/km
    GOOD,       // 🟡 Galben - 15-25 RON/km
    ACCEPTABLE, // 🟠 Portocaliu - 10-15 RON/km
    POOR        // 🔴 Roșu - < 10 RON/km
}
