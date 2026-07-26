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
import com.rideassistant.utils.toRON
import com.rideassistant.utils.toRonPerKm
import com.rideassistant.viewmodel.StatsViewModel

/**
 * Ecran statistici
 */
@Composable
fun StatsScreen(statsViewModel: StatsViewModel) {
    val stats = statsViewModel.stats.collectAsState()
    val isLoading = statsViewModel.isLoading.collectAsState()

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
                text = "Statistici",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Total rides
            StatCard(
                title = "Total Curse",
                value = stats.value.totalRides.toString(),
                icon = "🚗"
            )

            // Total profit
            StatCard(
                title = "Profit Total",
                value = stats.value.totalProfit.toRON(),
                icon = "💰"
            )

            // Average RON/km
            StatCard(
                title = "Medie RON/km",
                value = stats.value.averageRonPerKm.toRonPerKm(),
                icon = "📊"
            )

            // Total distance
            StatCard(
                title = "Distanță Totală",
                value = String.format("%.1f km", stats.value.totalDistance),
                icon = "🗟a️"
            )

            // Platform distribution
            Column(modifier = Modifier.padding(top = 16.dp)) {
                Text(
                    text = "Distribuție Platforme",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "BOLT: ${stats.value.boltRides}",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "UBER: ${stats.value.uberRides}",
                    fontSize = 12.sp
                )
            }
        }
    }
}

/**
 * Card statistic
 */
@Composable
fun StatCard(
    title: String,
    value: String,
    icon: String
) {
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
            Text(
                text = "$icon $title",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
