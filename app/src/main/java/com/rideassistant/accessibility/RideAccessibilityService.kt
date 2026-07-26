package com.rideassistant.accessibility

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import com.rideassistant.utils.Constants
import com.rideassistant.utils.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Accessibility Service pentru detectarea curselor Bolt/Uber
 * Monitorizează:
 * - Modificări UI
 * - Text care se schimbă
 * - Evenimente de touch
 * - Tranziții de ecran
 */
@AndroidEntryPoint
class RideAccessibilityService : AccessibilityService() {

    @Inject
    lateinit var accessibilityHelper: AccessibilityHelper

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var currentPlatform: String? = null
    private var lastEventTime: Long = 0
    private val eventDebounceMs = 500L // Evită duplicări

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        // Debounce pentru a evita procesare excesivă
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEventTime < eventDebounceMs) {
            return
        }
        lastEventTime = currentTime

        try {
            val sourceNode = event.source ?: return
            val packageName = event.packageName?.toString() ?: return

            // Determină platforma
            currentPlatform = when {
                packageName.contains(Constants.PACKAGE_BOLT) -> Constants.PLATFORM_BOLT
                packageName.contains(Constants.PACKAGE_UBER) -> Constants.PLATFORM_UBER
                else -> null
            }

            if (currentPlatform == null) {
                sourceNode.recycle()
                return
            }

            // Procesează evenimentul pe thread-ul IO
            scope.launch(Dispatchers.IO) {
                processAccessibilityEvent(event, sourceNode, currentPlatform!!)
            }

            sourceNode.recycle()
        } catch (e: Exception) {
            Logger.e("RideAccessibilityService", "Error processing event", e)
        }
    }

    /**
     * Procesează evenimentul de accesibilitate
     */
    private suspend fun processAccessibilityEvent(
        event: AccessibilityEvent,
        sourceNode: android.view.accessibility.AccessibilityNodeInfo,
        platform: String
    ) {
        try {
            when (event.eventType) {
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                    Timber.d("Window state changed on $platform")
                    accessibilityHelper.extractRideDataFromNode(sourceNode, platform)
                }
                AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                    Timber.d("Text changed on $platform")
                    accessibilityHelper.extractRideDataFromNode(sourceNode, platform)
                }
                AccessibilityEvent.TYPE_VIEW_SCROLLED -> {
                    Timber.d("View scrolled on $platform")
                }
            }
        } catch (e: Exception) {
            Logger.e("processAccessibilityEvent", "Error", e)
        }
    }

    override fun onInterrupt() {
        Timber.d("Accessibility Service interrupted")
    }

    override fun onServiceConnected() {
        Timber.d("Accessibility Service connected")
        super.onServiceConnected()
    }

    override fun onDestroy() {
        Timber.d("Accessibility Service destroyed")
        scope.coroutineContext.cancel()
        super.onDestroy()
    }
}
