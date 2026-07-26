package com.rideassistant.utils

import timber.log.Timber

/**
 * Constantele aplicației
 */
object Constants {
    // Platforms
    const val PLATFORM_BOLT = "BOLT"
    const val PLATFORM_UBER = "UBER"

    // Ride Status
    const val STATUS_PENDING = "PENDING"
    const val STATUS_ACCEPTED = "ACCEPTED"
    const val STATUS_COMPLETED = "COMPLETED"
    const val STATUS_REJECTED = "REJECTED"

    // Database
    const val DATABASE_NAME = "ride_assistant.db"

    // Preferences
    const val PREFERENCES_NAME = "ride_assistant_prefs"
    const val PREF_ACCESSIBILITY_ENABLED = "accessibility_enabled"
    const val PREF_OVERLAY_ENABLED = "overlay_enabled"
    const val PREF_SERVICE_RUNNING = "service_running"

    // Accessibility
    const val PACKAGE_BOLT = "com.bolt.client"
    const val PACKAGE_UBER = "com.ubercab"

    // OCR
    const val OCR_CONFIDENCE_THRESHOLD = 0.7f
    const val OCR_TEXT_MIN_LENGTH = 2

    // Overlay
    const val OVERLAY_DEFAULT_WIDTH = 250
    const val OVERLAY_DEFAULT_HEIGHT = 200
    const val OVERLAY_PADDING = 16

    // Timeout
    const val OCR_TIMEOUT_MS = 5000L
    const val ACCESSIBILITY_TIMEOUT_MS = 3000L

    // Notification
    const val NOTIFICATION_CHANNEL_ID = "ride_assistant_channel"
    const val NOTIFICATION_ID = 1001
}

/**
 * Logging helper
 */
object Logger {
    fun d(tag: String, message: String) {
        Timber.tag(tag).d(message)
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            Timber.tag(tag).e(throwable, message)
        } else {
            Timber.tag(tag).e(message)
        }
    }

    fun i(tag: String, message: String) {
        Timber.tag(tag).i(message)
    }

    fun w(tag: String, message: String) {
        Timber.tag(tag).w(message)
    }
}
