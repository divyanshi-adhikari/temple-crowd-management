package com.temple.crowdmanagement.features.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val uiState by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadDashboardData()
    }

    Scaffold(
        topBar = { DashboardTopBar() }
    ) { paddingValues ->
        if (isLoading) {
            AppLoadingScreen()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(SpiritualDarkBg),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
                item { 
                    HeaderSection(
                        name = uiState.devoteeName, 
                        isOpen = uiState.isTempleOpen
                    ) 
                }
                
                // Crowd Status - Hero Card
                item { 
                    CrowdStatusCard(
                        status = uiState.crowdStatus,
                        waitTime = uiState.waitTime,
                        totalVisitors = uiState.totalVisitors,
                        lastUpdated = uiState.lastUpdated,
                        crowdPercentage = uiState.crowdPercentage,
                        bestTime = uiState.bestTime,
                        confidence = uiState.predictionConfidence
                    )
                }
                
                // AI Prediction
                item {
                    CrowdPredictionCard(
                        prediction = uiState.aiPrediction,
                        confidence = uiState.predictionConfidence,
                        bestTime = uiState.bestTime
                    )
                }
                
                // Temple Timings
                item {
                    TempleTimingsCard(
                        openingTime = uiState.openingTime,
                        closingTime = uiState.closingTime,
                        nextAarti = uiState.nextAarti,
                        currentTime = uiState.currentTime
                    )
                }
                
               
                item { 
                    ActionsGrid(
                        onLiveMapClick = onLiveMapClick,
                        onBookDarshanClick = onBookDarshanClick,
                        onSOSClick = onSOSClick,
                        onTempleGuideClick = onTempleGuideClick
                    ) 
                }
                
                // Weather
                item {
                    WeatherCard(
                        temperature = uiState.temperature,
                        condition = uiState.weatherCondition,
                        feelsLike = uiState.feelsLike,
                        humidity = uiState.humidity,
                        windSpeed = uiState.windSpeed,
                        icon = uiState.weatherIcon
                    )
                }
                
                // Today's Events
                item { 
                    TodayEventsCard(events = uiState.todayEvents) 
                }
            }
        }
    }
}