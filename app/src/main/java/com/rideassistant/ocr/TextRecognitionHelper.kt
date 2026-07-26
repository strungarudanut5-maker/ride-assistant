package com.rideassistant.ocr

import com.rideassistant.models.RideData
import com.rideassistant.utils.Constants
import com.rideassistant.utils.Logger
import java.util.regex.Pattern

/**
 * Helper pentru recunoașterea și extragerea textului OCR
 */
class TextRecognitionHelper {

    /**
     * Extrage datele de cursă din text OCR
     */
    fun extract(text: String, platform: String): RideData {
        val cleanText = text.replace("\n", " ").lowercase()
        
        return when (platform) {
            Constants.PLATFORM_BOLT -> extractBoltData(cleanText)
            Constants.PLATFORM_UBER -> extractUberData(cleanText)
            else -> RideData()
        }
    }

    /**
     * Extrage datele de Bolt
     */
    private fun extractBoltData(text: String): RideData {
        Logger.d("TextRecognitionHelper", "Extracting Bolt data from: $text")

        // Preț
        val pricePattern = Pattern.compile("([0-9]+[.,][0-9]{2})\\s*(?:ron|lei| lei)")
        val priceMatcher = pricePattern.matcher(text)
        val price = if (priceMatcher.find()) {
            priceMatcher.group(1)?.replace(",", ".")?.toDoubleOrNull()
        } else null

        // Distanță
        val distancePattern = Pattern.compile("([0-9]+[.,][0-9]+)\\s*km")
        val distanceMatcher = distancePattern.matcher(text)
        val distance = if (distanceMatcher.find()) {
            distanceMatcher.group(1)?.replace(",", ".")?.toDoubleOrNull()
        } else null

        // Timp estimat
        val timePattern = Pattern.compile("([0-9]+)\\s*(?:min|minute)")
        val timeMatcher = timePattern.matcher(text)
        val time = if (timeMatcher.find()) {
            timeMatcher.group(1)?.toIntOrNull() ?: 0
        } else 0

        // Locații
        val locations = extractLocations(text)

        return RideData(
            platform = Constants.PLATFORM_BOLT,
            price = price,
            distance = distance,
            estimatedTime = time,
            pickupLocation = locations.first,
            dropoffLocation = locations.second,
            confidence = calculateConfidence(price, distance)
        )
    }

    /**
     * Extrage datele de Uber
     */
    private fun extractUberData(text: String): RideData {
        Logger.d("TextRecognitionHelper", "Extracting Uber data from: $text")

        // Preț
        val pricePattern = Pattern.compile("\\$?([0-9]+[.,][0-9]{2})")
        val priceMatcher = pricePattern.matcher(text)
        val price = if (priceMatcher.find()) {
            priceMatcher.group(1)?.replace(",", ".")?.toDoubleOrNull()
        } else null

        // Distanță
        val distancePattern = Pattern.compile("([0-9]+[.,][0-9]+)\\s*km")
        val distanceMatcher = distancePattern.matcher(text)
        val distance = if (distanceMatcher.find()) {
            distanceMatcher.group(1)?.replace(",", ".")?.toDoubleOrNull()
        } else null

        // Timp estimat
        val timePattern = Pattern.compile("([0-9]+)\\s*(?:min|mins|minute)")
        val timeMatcher = timePattern.matcher(text)
        val time = if (timeMatcher.find()) {
            timeMatcher.group(1)?.toIntOrNull() ?: 0
        } else 0

        // Locații
        val locations = extractLocations(text)

        return RideData(
            platform = Constants.PLATFORM_UBER,
            price = price,
            distance = distance,
            estimatedTime = time,
            pickupLocation = locations.first,
            dropoffLocation = locations.second,
            confidence = calculateConfidence(price, distance)
        )
    }

    /**
     * Extrage locațiile din text
     */
    private fun extractLocations(text: String): Pair<String, String> {
        // Simplă extragere - în aplicația reală ar trebui mai sofisticată
        val lines = text.split(" ")
        val pickup = lines.getOrNull(0) ?: "Unknown"
        val dropoff = lines.getOrNull(1) ?: "Unknown"
        return Pair(pickup, dropoff)
    }

    /**
     * Calculează încrederea în datele extrase
     */
    private fun calculateConfidence(price: Double?, distance: Double?): Float {
        var confidence = 0f
        if (price != null) confidence += 0.5f
        if (distance != null) confidence += 0.5f
        return confidence.coerceIn(0f, 1f)
    }
}
