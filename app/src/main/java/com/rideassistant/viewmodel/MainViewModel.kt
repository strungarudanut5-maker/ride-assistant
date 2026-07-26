package com.rideassistant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rideassistant.models.Ride
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
 * ViewModel pentru ecranul principal
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val rideRepository: RideRepository
) : ViewModel() {

    private val _rides = MutableStateFlow<List<Ride>>(emptyList())
    val rides: StateFlow<List<Ride>> = _rides.asStateFlow()

    private val _settings = MutableStateFlow<Settings?>(null)
    val settings: StateFlow<Settings?> = _settings.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadSettings()
        loadRides()
    }

    /**
     * Încărcă setările
     */
    fun loadSettings() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                rideRepository.getSettings().collect { setting ->
                    _settings.value = setting
                }
            } catch (e: Exception) {
                Logger.e("MainViewModel", "Error loading settings", e)
                _error.value = "Eroare încărcarea setărilor"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Încărcă cursele
     */
    fun loadRides() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                rideRepository.getAllRides().collect { ridesList ->
                    _rides.value = ridesList
                }
            } catch (e: Exception) {
                Logger.e("MainViewModel", "Error loading rides", e)
                _error.value = "Eroare încărcarea curselor"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Salvează o cursă
     */
    fun saveRide(ride: Ride) {
        viewModelScope.launch {
            try {
                rideRepository.insertRide(ride)
                Logger.d("MainViewModel", "Ride saved successfully")
            } catch (e: Exception) {
                Logger.e("MainViewModel", "Error saving ride", e)
                _error.value = "Eroare salvare cursă"
            }
        }
    }

    /**
     * Actualizează setări
     */
    fun updateSettings(settings: Settings) {
        viewModelScope.launch {
            try {
                rideRepository.updateSettings(settings)
                _settings.value = settings
                Logger.d("MainViewModel", "Settings updated successfully")
            } catch (e: Exception) {
                Logger.e("MainViewModel", "Error updating settings", e)
                _error.value = "Eroare actualizare setări"
            }
        }
    }
}
