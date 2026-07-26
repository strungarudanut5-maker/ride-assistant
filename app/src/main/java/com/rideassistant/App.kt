package com.rideassistant

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Application class cu Hilt Dependency Injection
 * Inițializează logging și alte componente globale
 */
@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Inițializare Timber logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        
        Timber.d("Ride Assistant Application started")
    }
}
