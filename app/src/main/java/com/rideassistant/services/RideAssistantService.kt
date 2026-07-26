package com.rideassistant.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.rideassistant.MainActivity
import com.rideassistant.R
import com.rideassistant.repository.RideRepository
import com.rideassistant.utils.Constants
import com.rideassistant.utils.Logger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

/**
 * Foreground Service pentru rularea în fundal
 * Monitorizează aplicații și detectează curse
 */
@AndroidEntryPoint
class RideAssistantService : Service() {

    @Inject
    lateinit var rideRepository: RideRepository

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var serviceJob: Job? = null

    override fun onCreate() {
        super.onCreate()
        Timber.d("RideAssistantService created")
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("RideAssistantService started")

        // Pornire notificare persistentă
        startForeground(Constants.NOTIFICATION_ID, createNotification())

        // Pornire serviciu de monitorizare
        startMonitoring()

        return START_STICKY
    }

    /**
     * Pornire monitorizare curse
     */
    private fun startMonitoring() {
        Logger.d("RideAssistantService", "Starting monitoring")
        // Laț implementare reală a monitorizării
    }

    /**
     * Crează canal de notificare
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_ID,
                "Ride Assistant",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Serviciul Ride Assistant rulează"
                enableVibration(false)
                enableLights(false)
                setSound(null, null)
            }

            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    /**
     * Crează notificare persistentă
     */
    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Ride Assistant")
            .setContentText("Serviciul rulează în fundal...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    override fun onDestroy() {
        Timber.d("RideAssistantService destroyed")
        serviceJob?.cancel()
        scope.coroutineContext.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
