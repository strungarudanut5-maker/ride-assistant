package com.rideassistant.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rideassistant.models.Ride
import com.rideassistant.utils.toFormattedDate
import com.rideassistant.utils.toKmFormat
import com.rideassistant.utils.toRON
import com.rideassistant.utils.toRonPerKm
import com.rideassistant.viewmodel.MainViewModel

/**
 * Ecran principal
 */
@Composable
fun HomeScreen(mainViewModel: MainViewModel) {
    val rides = mainViewModel.rides.collectAsState()
    val isLoading = mainViewModel.isLoading.collectAsState()

    if (isLoading.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Curse Recente",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (rides.value.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nu sunt curse disponibile",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            } else {
                rides.value.take(5).forEach { ride ->
                    RideCard(ride)
                }
            }
        }
    }
}

/**
 * Card pentru o cursă
 */
@Composable
fun RideCard(ride: Ride) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header
            Column {
                Text(
                    text = "${ride.platform} - ${ride.createdAt.toFormattedDate()}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Locatii
            Text(
                text = "De la: ${ride.pickupLocation}",
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "Spre: ${ride.dropoffLocation}",
                fontSize = 12.sp
            )

            // Detalii
            Column(modifier = Modifier.padding(top = 12.dp)) {
                Text(
                    text = "💰 Preț: ${ride.price.toRON()}",
                    fontSize = 12.sp
                )
                Text(
                    text = "📍 Distanță: ${ride.distance.toKmFormat()}",
                    fontSize = 12.sp
                )
                Text(
                    text = "📊 RON/km: ${ride.ronPerKm.toRonPerKm()}",
                    fontSize = 12.sp,
                    color = when {
                        ride.ronPerKm > 25 -> MaterialTheme.colorScheme.primary
                        ride.ronPerKm > 15 -> MaterialTheme.colorScheme.tertiary
                        else -> MaterialTheme.colorScheme.error
                    }
                )
                Text(
                    text = "✅ Profit: ${ride.netProfit.toRON()}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        ride.netProfit > 25 -> MaterialTheme.colorScheme.primary
                        ride.netProfit > 15 -> MaterialTheme.colorScheme.tertiary
                        else -> MaterialTheme.colorScheme.error
                    }
                )
            }
        }
    }
}
