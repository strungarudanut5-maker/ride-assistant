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

data class StatsData(
    val totalRides: Long = 0,
    val totalProfit: Double = 0.0,
    val averageRonPerKm: Double = 0.0,
    val totalDistance: Double = 0.0,
    val totalHours: Int = 0,
    val boltRides: Long = 0,
    val uberRides: Long = 0,
    val bestRide: Ride? = null,
    val worstRide: Ride? = null
)

/**
 * ViewModel pentru ecranul de statistici
 */
@HiltViewModel
class StatsViewModel @Inject constructor(
    private val rideRepository: RideRepository
) : ViewModel() {

    private val _stats = MutableStateFlow(StatsData())
    val stats: StateFlow<StatsData> = _stats.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _rides = MutableStateFlow<List<Ride>>(emptyList())

    init {
        loadStats()
    }

    /**
     * Încărcă statistici
     */
    fun loadStats() {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                rideRepository.getAllRides().collect { rides ->
                    _rides.value = rides

                    val totalRides = rides.size.toLong()
                    val totalProfit = rides.sumOf { it.netProfit }
                    val averageRonPerKm = rides.map { it.ronPerKm }.average()
                    val totalDistance = rides.sumOf { it.distance }
                    val boltRides = rides.count { it.platform == "BOLT" }.toLong()
                    val uberRides = rides.count { it.platform == "UBER" }.toLong()

                    val bestRide = rides.maxByOrNull { it.profitScore }
                    val worstRide = rides.minByOrNull { it.profitScore }

                    _stats.value = StatsData(
                        totalRides = totalRides,
                        totalProfit = totalProfit,
                        averageRonPerKm = averageRonPerKm,
                        totalDistance = totalDistance,
                        boltRides = boltRides,
                        uberRides = uberRides,
                        bestRide = bestRide,
                        worstRide = worstRide
                    )
                }
            } catch (e: Exception) {
                Logger.e("StatsViewModel", "Error loading stats", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
