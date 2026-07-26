package com.rideassistant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rideassistant.models.Settings
import com.rideassistant.repository.RideRepository
import com.rideassistant.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel pentru ecranul de setări
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val rideRepository: RideRepository
) : ViewModel() {

    private val _settings = MutableStateFlow(Settings())
    val settings: StateFlow<Settings> = _settings.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    init {
        loadSettings()
    }

    /**
     * Încărcă setările
     */
    fun loadSettings() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                rideRepository.getSettings().collect { setting ->
                    _settings.value = setting ?: Settings()
                }
            } catch (e: Exception) {
                Logger.e("SettingsViewModel", "Error loading settings", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Salvează setările
     */
    fun saveSettings(settings: Settings) {
        viewModelScope.launch {
            try {
                _isSaving.value = true
                rideRepository.updateSettings(settings)
                _settings.value = settings
                Logger.d("SettingsViewModel", "Settings saved successfully")
            } catch (e: Exception) {
                Logger.e("SettingsViewModel", "Error saving settings", e)
            } finally {
                _isSaving.value = false
            }
        }
    }

    /**
     * Actualizează consum combustibil
     */
    fun updateFuelConsumption(consumption: Double) {
        val updated = _settings.value.copy(fuelConsumptionPer100km = consumption)
        _settings.value = updated
    }

    /**
     * Actualizează preț combustibil
     */
    fun updateFuelPrice(price: Double) {
        val updated = _settings.value.copy(fuelPrice = price)
        _settings.value = updated
    }

    /**
     * Actualizează comision Bolt
     */
    fun updateBoltCommission(commission: Double) {
        val updated = _settings.value.copy(boltCommissionPercent = commission)
        _settings.value = updated
    }

    /**
     * Actualizează comision Uber
     */
    fun updateUberCommission(commission: Double) {
        val updated = _settings.value.copy(uberCommissionPercent = commission)
        _settings.value = updated
    }
}
