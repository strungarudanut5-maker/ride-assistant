package com.rideassistant.services

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.rideassistant.MainActivity
import com.rideassistant.R
import com.rideassistant.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manager pentru notificări
 */
@Singleton
class NotificationManagerHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    /**
     * Afișează notificare simplu
     */
    fun showNotification(
        title: String,
        message: String,
        notificationId: Int = Constants.NOTIFICATION_ID + 1
    ) {
        val notification = NotificationCompat.Builder(
            context,
            Constants.NOTIFICATION_CHANNEL_ID
        )
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(notificationId, notification)
    }

    /**
     * Afișează notificare pentru cursă bună
     */
    fun showGoodRideNotification(
        platform: String,
        profit: Double,
        ronPerKm: Double
    ) {
        showNotification(
            title = "🟢 Cursă Bună!",
            message = "$platform: ${profit} RON (${ronPerKm} RON/km)",
            notificationId = Constants.NOTIFICATION_ID + 2
        )
    }

    /**
     * Afișează notificare pentru cursă proastă
     */
    fun showPoorRideNotification(
        platform: String,
        profit: Double,
        ronPerKm: Double
    ) {
        showNotification(
            title = "🔴 Cursă de Evitat",
            message = "$platform: ${profit} RON (${ronPerKm} RON/km)",
            notificationId = Constants.NOTIFICATION_ID + 3
        )
    }
}
