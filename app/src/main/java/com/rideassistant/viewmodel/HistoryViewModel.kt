package com.rideassistant.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rideassistant.models.Ride
import com.rideassistant.repository.RideRepository
import com.rideassistant.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel pentru ecranul de istoric
 */
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val rideRepository: RideRepository
) : ViewModel() {

    private val _rides = MutableStateFlow<List<Ride>>(emptyList())
    val rides: StateFlow<List<Ride>> = _rides.asStateFlow()

    private val _filteredRides = MutableStateFlow<List<Ride>>(emptyList())
    val filteredRides: StateFlow<List<Ride>> = _filteredRides.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadRides()
    }

    /**
     * Încărcă toate cursele
     */
    fun loadRides() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                rideRepository.getAllRides().collect { ridesList ->
                    _rides.value = ridesList
                    _filteredRides.value = ridesList
                }
            } catch (e: Exception) {
                Logger.e("HistoryViewModel", "Error loading rides", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Filtrează după platformă
     */
    fun filterByPlatform(platform: String) {
        _filteredRides.value = _rides.value.filter { it.platform == platform }
    }

    /**
     * Filtrează după status
     */
    fun filterByStatus(status: String) {
        _filteredRides.value = _rides.value.filter { it.status == status }
    }

    /**
     * Filtrează după periiă de timp
     */
    fun filterByDateRange(startTime: Long, endTime: Long) {
        _filteredRides.value = _rides.value.filter {
            it.createdAt >= startTime && it.createdAt <= endTime
        }
    }

    /**
     * Șterge o cursă
     */
    fun deleteRide(ride: Ride) {
        viewModelScope.launch {
            try {
                rideRepository.deleteRide(ride)
                Logger.d("HistoryViewModel", "Ride deleted successfully")
            } catch (e: Exception) {
                Logger.e("HistoryViewModel", "Error deleting ride", e)
            }
        }
    }
}
