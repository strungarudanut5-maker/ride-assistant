package com.rideassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.rideassistant.ui.screens.HistoryScreen
import com.rideassistant.ui.screens.HomeScreen
import com.rideassistant.ui.screens.MapsScreen
import com.rideassistant.ui.screens.SettingsScreen
import com.rideassistant.ui.screens.StatsScreen
import com.rideassistant.ui.theme.RideAssistantTheme
import com.rideassistant.viewmodel.HistoryViewModel
import com.rideassistant.viewmodel.MainViewModel
import com.rideassistant.viewmodel.SettingsViewModel
import com.rideassistant.viewmodel.StatsViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * Activity principal cu navigare bottom tab
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("MainActivity created")

        setContent {
            RideAssistantTheme {
                MainScreen()
            }
        }
    }
}

/**
 * Ecran principal cu navigare
 */
@Composable
fun MainScreen() {
    var selectedTab by remember { mutableStateOf(0) }

    val mainViewModel: MainViewModel = hiltViewModel()
    val historyViewModel: HistoryViewModel = hiltViewModel()
    val statsViewModel: StatsViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Acasă") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "History") },
                    label = { Text("Istoric") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "Stats") },
                    label = { Text("Statistici") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.LocationOn, contentDescription = "Maps") },
                    label = { Text("Hărți") },
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Setări") },
                    selected = selectedTab == 4,
                    onClick = { selectedTab = 4 }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> HomeScreen(mainViewModel)
                1 -> HistoryScreen(historyViewModel)
                2 -> StatsScreen(statsViewModel)
                3 -> MapsScreen()
                4 -> SettingsScreen(settingsViewModel)
            }
        }
    }
}
