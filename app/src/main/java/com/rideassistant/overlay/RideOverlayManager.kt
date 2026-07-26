package com.rideassistant.overlay

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.rideassistant.R
import com.rideassistant.models.ProfitLevel
import com.rideassistant.models.ProfitResult
import com.rideassistant.utils.Constants
import com.rideassistant.utils.Logger
import com.rideassistant.utils.toRON
import com.rideassistant.utils.toRonPerKm
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manager pentru overlay-ul care afișează datele curselor
 */
@Singleton
class RideOverlayManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var overlayView: OverlayView? = null
    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    /**
     * Afișează overlay-ul pe ecran
     */
    fun showOverlay(profitResult: ProfitResult, price: Double, distance: Double) {
        try {
            if (overlayView != null) {
                hideOverlay()
            }

            overlayView = OverlayView(context, profitResult, price, distance)
            val params = WindowManager.LayoutParams().apply {
                type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                format = PixelFormat.TRANSLUCENT
                flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                width = Constants.OVERLAY_DEFAULT_WIDTH
                height = Constants.OVERLAY_DEFAULT_HEIGHT
                x = 0
                y = 0
                gravity = Gravity.TOP or Gravity.START
            }

            windowManager.addView(overlayView, params)
            Logger.d("RideOverlayManager", "Overlay shown")
        } catch (e: Exception) {
            Logger.e("RideOverlayManager", "Error showing overlay", e)
        }
    }

    /**
     * Ascunde overlay-ul
     */
    fun hideOverlay() {
        try {
            overlayView?.let {
                windowManager.removeView(it)
                overlayView = null
            }
            Logger.d("RideOverlayManager", "Overlay hidden")
        } catch (e: Exception) {
            Logger.e("RideOverlayManager", "Error hiding overlay", e)
        }
    }

    /**
     * Actualizează datele afișate
     */
    fun updateOverlay(profitResult: ProfitResult, price: Double, distance: Double) {
        try {
            overlayView?.updateData(profitResult, price, distance)
        } catch (e: Exception) {
            Logger.e("RideOverlayManager", "Error updating overlay", e)
        }
    }
}

/**
 * Custom view pentru overlay
 */
class OverlayView(
    context: Context,
    private var profitResult: ProfitResult,
    private var price: Double,
    private var distance: Double
) : FrameLayout(context) {

    private lateinit var priceText: TextView
    private lateinit var kmText: TextView
    private lateinit var ronPerKmText: TextView
    private lateinit var profitText: TextView
    private lateinit var scoreText: TextView
    private var initialX = 0f
    private var initialY = 0f
    private var initialTouchX = 0f
    private var initialTouchY = 0f

    init {
        setupUI()
    }

    private fun setupUI() {
        setBackgroundColor(Color.parseColor("#1a1a1a"))
        setPadding(8, 8, 8, 8)

        val container = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }

        // Preț
        priceText = TextView(context).apply {
            text = "💰 ${price.toRON()}"
            textSize = 12f
            setTextColor(Color.WHITE)
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        // Distanță
        kmText = TextView(context).apply {
            text = "📍 ${distance}km"
            textSize = 12f
            setTextColor(Color.WHITE)
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        // RON/Km
        ronPerKmText = TextView(context).apply {
            text = "📊 ${profitResult.ronPerKm.toRonPerKm()}"
            textSize = 12f
            setTextColor(getProfitColor())
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        // Profit
        profitText = TextView(context).apply {
            text = "✅ ${profitResult.netProfit.toRON()}"
            textSize = 12f
            setTextColor(getProfitColor())
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        // Scor
        scoreText = TextView(context).apply {
            text = "⭐ ${profitResult.profitScore}/100"
            textSize = 12f
            setTextColor(getProfitColor())
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        container.addView(priceText)
        container.addView(kmText)
        container.addView(ronPerKmText)
        container.addView(profitText)
        container.addView(scoreText)

        addView(container)

        setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = x
                    initialY = y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = event.rawX - initialTouchX
                    val deltaY = event.rawY - initialTouchY
                    x = initialX + deltaX
                    y = initialY + deltaY
                    true
                }
                else -> false
            }
        }
    }

    private fun getProfitColor(): Int {
        return when (profitResult.profitLevel) {
            ProfitLevel.EXCELLENT -> Color.parseColor("#4caf50") // Verde
            ProfitLevel.GOOD -> Color.parseColor("#ffc107")       // Galben
            ProfitLevel.ACCEPTABLE -> Color.parseColor("#ff9800") // Portocaliu
            ProfitLevel.POOR -> Color.parseColor("#f44336")       // Roșu
        }
    }

    fun updateData(profitResult: ProfitResult, price: Double, distance: Double) {
        this.profitResult = profitResult
        this.price = price
        this.distance = distance

        priceText.text = "💰 ${price.toRON()}"
        kmText.text = "📍 ${distance}km"
        ronPerKmText.text = "📊 ${profitResult.ronPerKm.toRonPerKm()}"
        ronPerKmText.setTextColor(getProfitColor())
        profitText.text = "✅ ${profitResult.netProfit.toRON()}"
        profitText.setTextColor(getProfitColor())
        scoreText.text = "⭐ ${profitResult.profitScore}/100"
        scoreText.setTextColor(getProfitColor())
    }
}
