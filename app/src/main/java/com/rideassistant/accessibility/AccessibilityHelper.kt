package com.rideassistant.accessibility

import android.view.accessibility.AccessibilityNodeInfo
import com.rideassistant.models.RideData
import com.rideassistant.utils.Constants
import com.rideassistant.utils.Logger
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Helper pentru extragerea datelor prin Accessibility Service
 */
@Singleton
class AccessibilityHelper @Inject constructor() {

    /**
     * Extrage datele curselor din accessibility node
     */
    suspend fun extractRideDataFromNode(
        node: AccessibilityNodeInfo,
        platform: String
    ): RideData? {
        return try {
            val textNodes = mutableListOf<String>()
            collectTextFromNode(node, textNodes)
            val allText = textNodes.joinToString(" ")

            Logger.d("AccessibilityHelper", "Extracted text: $allText")

            // Parsează datele în funcție de platformă
            when (platform) {
                Constants.PLATFORM_BOLT -> parseBoltRideData(allText)
                Constants.PLATFORM_UBER -> parseUberRideData(allText)
                else -> null
            }
        } catch (e: Exception) {
            Logger.e("AccessibilityHelper", "Error extracting data", e)
            null
        }
    }

    /**
     * Colectează recursiv tot textul din node
     */
    private fun collectTextFromNode(
        node: AccessibilityNodeInfo,
        texts: MutableList<String>
    ) {
        // Adaugă textul nodului curent
        node.text?.takeIf { it.isNotEmpty() }?.let { texts.add(it.toString()) }
        node.contentDescription?.takeIf { it.isNotEmpty() }?.let { texts.add(it.toString()) }

        // Parcurge copiii
        for (i in 0 until node.childCount) {
            try {
                node.getChild(i)?.let { child ->
                    collectTextFromNode(child, texts)
                    child.recycle()
                }
            } catch (e: Exception) {
                Logger.e("collectTextFromNode", "Error processing child", e)
            }
        }
    }

    /**
     * Parsează datele de cursă Bolt
     */
    private fun parseBoltRideData(text: String): RideData {
        // Regex patterns pentru Bolt
        val pricePattern = "([0-9]+[.,][0-9]{2})\\s*(?:RON|lei)".toRegex()
        val distancePattern = "([0-9]+[.,][0-9]+)\\s*km".toRegex()
        val timePattern = "([0-9]+)\\s*(?:min|minute)".toRegex()

        val price = pricePattern.find(text)?.groupValues?.get(1)?.toDoubleOrNull()
        val distance = distancePattern.find(text)?.groupValues?.get(1)?.toDoubleOrNull()
        val time = timePattern.find(text)?.groupValues?.get(1)?.toIntOrNull() ?: 0

        return RideData(
            platform = Constants.PLATFORM_BOLT,
            price = price,
            distance = distance,
            estimatedTime = time,
            confidence = if (price != null && distance != null) 0.8f else 0.5f
        )
    }

    /**
     * Parsează datele de cursă Uber
     */
    private fun parseUberRideData(text: String): RideData {
        // Regex patterns pentru Uber
        val pricePattern = "\\$?([0-9]+[.,][0-9]{2})".toRegex()
        val distancePattern = "([0-9]+[.,][0-9]+)\\s*(?:km|miles)".toRegex()
        val timePattern = "([0-9]+)\\s*(?:min|mins|minute)".toRegex()

        val price = pricePattern.find(text)?.groupValues?.get(1)?.toDoubleOrNull()
        val distance = distancePattern.find(text)?.groupValues?.get(1)?.toDoubleOrNull()
        val time = timePattern.find(text)?.groupValues?.get(1)?.toIntOrNull() ?: 0

        return RideData(
            platform = Constants.PLATFORM_UBER,
            price = price,
            distance = distance,
            estimatedTime = time,
            confidence = if (price != null && distance != null) 0.8f else 0.5f
        )
    }
}
