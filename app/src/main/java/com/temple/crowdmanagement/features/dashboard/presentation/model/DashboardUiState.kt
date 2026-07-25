package com.temple.crowdmanagement.features.dashboard.presentation.model

data class DashboardUiState(
    val devoteeName: String = "",
    val isTempleOpen: Boolean = false,
    val crowdStatus: String = "",
    val waitTime: String = "",
    val totalVisitors: String = "",
    val lastUpdated: String = "",
    val crowdPercentage: Int = 0,
    val aiPrediction: String = "",
    val predictionConfidence: Int = 0,
    val bestTime: String = "",
    val temperature: Int = 0,
    val weatherCondition: String = "",
    val feelsLike: String = "",
    val humidity: Int = 0,
    val windSpeed: String = "",
    val weatherIcon: String = "",
    val openingTime: String = "",
    val closingTime: String = "",
    val nextAarti: String = "",
    val currentTime: String = "",
    val todayEvents: List<String> = emptyList(),
    val importantNotices: List<String> = emptyList()
)