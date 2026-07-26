package com.rideassistant.utils

import com.rideassistant.models.ProfitLevel
import com.rideassistant.models.ProfitResult
import com.rideassistant.models.Settings
import kotlin.math.roundToInt

/**
 * Calculatoare de profit cu logică complexă
 */
object ProfitCalculator {
    /**
     * Calculează profitul pentru o cursă
     */
    fun calculateProfit(
        price: Double,
        distance: Double,
        estimatedTimeMinutes: Int,
        settings: Settings,
        platform: String
    ): ProfitResult {
        // Calcul cost combustibil
        val fuelCost = (distance / 100) * settings.fuelConsumptionPer100km * settings.fuelPrice

        // Calcul comision
        val commission = when (platform.uppercase()) {
            "BOLT" -> price * (settings.boltCommissionPercent / 100)
            "UBER" -> price * (settings.uberCommissionPercent / 100)
            else -> 0.0
        }

        // Calcul profit brut
        val grossProfit = price - fuelCost - commission

        // Calcul profit net (fără alte costuri)
        val netProfit = grossProfit

        // RON per km
        val ronPerKm = if (distance > 0) grossProfit / distance else 0.0

        // RON per oră
        val estimatedHours = estimatedTimeMinutes / 60.0
        val ronPerHour = if (estimatedHours > 0) grossProfit / estimatedHours else 0.0

        // Scor profit (0-100)
        val profitScore = calculateProfitScore(ronPerKm, settings)

        // Determinare nivel profit
        val profitLevel = when {
            ronPerKm > settings.goodProfit -> ProfitLevel.EXCELLENT
            ronPerKm > settings.acceptableProfit -> ProfitLevel.GOOD
            ronPerKm > settings.minimumRonPerKm -> ProfitLevel.ACCEPTABLE
            else -> ProfitLevel.POOR
        }

        return ProfitResult(
            grossProfit = grossProfit.roundToTwoDecimals(),
            fuelCost = fuelCost.roundToTwoDecimals(),
            commission = commission.roundToTwoDecimals(),
            netProfit = netProfit.roundToTwoDecimals(),
            ronPerKm = ronPerKm.roundToTwoDecimals(),
            ronPerHour = ronPerHour.roundToTwoDecimals(),
            profitScore = profitScore,
            profitLevel = profitLevel
        )
    }

    /**
     * Calculează scor profit (0-100)
     */
    private fun calculateProfitScore(
        ronPerKm: Double,
        settings: Settings
    ): Int {
        return when {
            ronPerKm >= settings.goodProfit -> 100
            ronPerKm >= settings.acceptableProfit -> 75
            ronPerKm >= settings.minimumRonPerKm -> 50
            ronPerKm > 0 -> 25
            else -> 0
        }.coerceIn(0, 100)
    }

    /**
     * Determină culoarea pentru profit
     */
    fun getProfitColor(level: ProfitLevel): String = when (level) {
        ProfitLevel.EXCELLENT -> "#4caf50"  // Verde
        ProfitLevel.GOOD -> "#ffc107"       // Galben
        ProfitLevel.ACCEPTABLE -> "#ff9800" // Portocaliu
        ProfitLevel.POOR -> "#f44336"       // Roșu
    }

    /**
     * Determină emoji pentru profit
     */
    fun getProfitEmoji(level: ProfitLevel): String = when (level) {
        ProfitLevel.EXCELLENT -> "🟢"
        ProfitLevel.GOOD -> "🟡"
        ProfitLevel.ACCEPTABLE -> "🟠"
        ProfitLevel.POOR -> "🔴"
    }
}

/**
 * Extensie pentru rotunjire la 2 zecimale
 */
fun Double.roundToTwoDecimals(): Double = (this * 100).roundToInt() / 100.0
