package com.temple.crowdmanagement.features.dashboard.presentation.repository

import com.temple.crowdmanagement.features.dashboard.presentation.model.DashboardUiState
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

class DashboardRepository {
    
    suspend fun getDashboardData(): DashboardUiState {
        delay(1500)
        
        val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        
        return DashboardUiState(
            devoteeName = "Devotee",
            isTempleOpen = true,
            crowdStatus = "Low",
            waitTime = "12 mins",
            totalVisitors = "24,510",
            lastUpdated = "2 mins ago",
            crowdPercentage = 35,
            aiPrediction = "Moderate Crowd Expected",
            predictionConfidence = 92,
            bestTime = "3 PM - 4 PM",
            temperature = 28,
            weatherCondition = "Sunny",
            feelsLike = "30°C",
            humidity = 60,
            windSpeed = "8 km/h",
            weatherIcon = "🌤",
            openingTime = "6:30 AM",
            closingTime = "9:30 PM",
            nextAarti = "7:00 PM",
            currentTime = currentTime,
            todayEvents = listOf(
                "Mangala Aarti",
                "Shringar Darshan",
                "Evening Aarti"
            ),
            importantNotices = listOf(
                "Carry Water Bottle",
                "Follow Queue System",
                "Shoes Counter Available"
            )
        )
    }
}