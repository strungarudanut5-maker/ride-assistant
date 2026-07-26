package com.rideassistant.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rideassistant.models.Settings
import kotlinx.coroutines.flow.Flow

/**
 * DAO pentru operații cu setări
 */
@Dao
interface SettingsDao {
    @Insert
    suspend fun insertSettings(settings: Settings)

    @Update
    suspend fun updateSettings(settings: Settings)

    @Query("SELECT * FROM settings WHERE id = 1")
    fun getSettings(): Flow<Settings?>

    @Query("SELECT * FROM settings WHERE id = 1")
    suspend fun getSettingsDirect(): Settings?
}
