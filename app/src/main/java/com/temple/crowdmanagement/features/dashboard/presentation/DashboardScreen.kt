package com.temple.crowdmanagement.features.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.temple.crowdmanagement.features.dashboard.presentation.components.*
import com.temple.crowdmanagement.ui.theme.SpiritualDarkBg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: HomeViewModel = viewModel(),
    onLiveMapClick: () -> Unit = {},
    onBookDarshanClick: () -> Unit = {},
    onSOSClick: () -> Unit = {},
    onTempleGuideClick: () -> Unit = {}
) {
    val uiState   by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadDashboardData() }

    // The top bar is a custom Box (not TopAppBar) so we don't use Scaffold's topBar slot
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpiritualDarkBg)
    ) {
        // Maroon header (handles its own top padding / status bar area)
        DashboardTopBar(
            isTempleOpen   = uiState.isTempleOpen,
            crowdFlowLabel = when (uiState.crowdStatus.lowercase()) {
                "low"      -> "Smooth Flow"
                "moderate" -> "Moderate Flow"
                "high"     -> "Heavy Flow"
                else       -> "Smooth Flow"
            }
        )

        if (isLoading) {
            AppLoadingScreen()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SpiritualDarkBg),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Welcome greeting
                item {
                    HeaderSection(
                        name   = uiState.devoteeName,
                        isOpen = uiState.isTempleOpen
                    )
                }

                // Hero — Live Crowd Ring Gauge
                item {
                    CrowdStatusCard(
                        status          = uiState.crowdStatus,
                        waitTime        = uiState.waitTime,
                        totalVisitors   = uiState.totalVisitors,
                        lastUpdated     = uiState.lastUpdated,
                        crowdPercentage = uiState.crowdPercentage,
                        bestTime        = uiState.bestTime,
                        confidence      = uiState.predictionConfidence
                    )
                }

                // Temple Timings (Aarti times are key pilgrim info)
                item {
                    TempleTimingsCard(
                        openingTime = uiState.openingTime,
                        closingTime = uiState.closingTime,
                        nextAarti   = uiState.nextAarti,
                        currentTime = uiState.currentTime
                    )
                }

                // Primary & secondary action rows
                item {
                    ActionsGrid(
                        onLiveMapClick      = onLiveMapClick,
                        onBookDarshanClick  = onBookDarshanClick,
                        onSOSClick          = onSOSClick,
                        onTempleGuideClick  = onTempleGuideClick
                    )
                }

                // AI Prediction
                item {
                    CrowdPredictionCard(
                        prediction = uiState.aiPrediction,
                        confidence = uiState.predictionConfidence,
                        bestTime   = uiState.bestTime
                    )
                }

                // Weather
                item {
                    WeatherCard(
                        temperature = uiState.temperature,
                        condition   = uiState.weatherCondition,
                        feelsLike   = uiState.feelsLike,
                        humidity    = uiState.humidity,
                        windSpeed   = uiState.windSpeed,
                        icon        = uiState.weatherIcon
                    )
                }

                // Today's Events
                item { TodayEventsCard(events = uiState.todayEvents) }

                // Bottom padding so FAB doesn't overlap last card
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}