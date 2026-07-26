package com.rideassistant.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manager pentru preferințe locale
 */
@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE)

    /**
     * Salvează o valoare boolean
     */
    fun setBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    /**
     * Preiau o valoare boolean
     */
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    /**
     * Salvează o valoare string
     */
    fun setString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    /**
     * Preiau o valoare string
     */
    fun getString(key: String, defaultValue: String = ""): String {
        return preferences.getString(key, defaultValue) ?: defaultValue
    }

    /**
     * Salvează o valoare int
     */
    fun setInt(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    /**
     * Preiau o valoare int
     */
    fun getInt(key: String, defaultValue: Int = 0): Int {
        return preferences.getInt(key, defaultValue)
    }

    /**
     * Salvează o valoare long
     */
    fun setLong(key: String, value: Long) {
        preferences.edit().putLong(key, value).apply()
    }

    /**
     * Preiau o valoare long
     */
    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return preferences.getLong(key, defaultValue)
    }

    /**
     * Șterge o preferință
     */
    fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }

    /**
     * Șterge toate preferințele
     */
    fun clear() {
        preferences.edit().clear().apply()
    }
}
