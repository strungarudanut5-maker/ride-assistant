package com.rideassistant.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rideassistant.viewmodel.SettingsViewModel

/**
 * Ecran setări
 */
@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel) {
    val settings = settingsViewModel.settings.collectAsState()
    val isLoading = settingsViewModel.isLoading.collectAsState()
    val isSaving = settingsViewModel.isSaving.collectAsState()

    if (isLoading.value) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Setări",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Mașină
            Text(
                text = "Parametri Mașină",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 16.dp)
            )

            val fuelConsumption = remember { mutableStateOf(settings.value.fuelConsumptionPer100km.toString()) }
            OutlinedTextField(
                value = fuelConsumption.value,
                onValueChange = { fuelConsumption.value = it },
                label = { Text("Consum combustibil (l/100km)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            val fuelPrice = remember { mutableStateOf(settings.value.fuelPrice.toString()) }
            OutlinedTextField(
                value = fuelPrice.value,
                onValueChange = { fuelPrice.value = it },
                label = { Text("Preț combustibil (RON/l)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            // Comisioane
            Text(
                text = "Comisioane Platforme",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 16.dp)
            )

            val boltCommission = remember { mutableStateOf(settings.value.boltCommissionPercent.toString()) }
            OutlinedTextField(
                value = boltCommission.value,
                onValueChange = { boltCommission.value = it },
                label = { Text("Comision Bolt (%)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            val uberCommission = remember { mutableStateOf(settings.value.uberCommissionPercent.toString()) }
            OutlinedTextField(
                value = uberCommission.value,
                onValueChange = { uberCommission.value = it },
                label = { Text("Comision Uber (%)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            // Praguri profit
            Text(
                text = "Praguri Profit",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 16.dp)
            )

            val minimumProfit = remember { mutableStateOf(settings.value.minimumProfit.toString()) }
            OutlinedTextField(
                value = minimumProfit.value,
                onValueChange = { minimumProfit.value = it },
                label = { Text("Profit minim (RON)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            // Buton salvare
            Button(
                onClick = {
                    val updatedSettings = settings.value.copy(
                        fuelConsumptionPer100km = fuelConsumption.value.toDoubleOrNull() ?: 8.0,
                        fuelPrice = fuelPrice.value.toDoubleOrNull() ?: 6.0,
                        boltCommissionPercent = boltCommission.value.toDoubleOrNull() ?: 20.0,
                        uberCommissionPercent = uberCommission.value.toDoubleOrNull() ?: 25.0,
                        minimumProfit = minimumProfit.value.toDoubleOrNull() ?: 10.0
                    )
                    settingsViewModel.saveSettings(updatedSettings)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 16.dp),
                enabled = !isSaving.value
            ) {
                if (isSaving.value) {
                    CircularProgressIndicator()
                } else {
                    Text("Salvare Setări")
                }
            }
        }
    }
}
