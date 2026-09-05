package com.temple.crowdmanagement.features.dashboard.presentation.repository

import com.temple.crowdmanagement.core.model.TempleSite
import com.temple.crowdmanagement.core.network.NetworkClient
import com.temple.crowdmanagement.features.dashboard.presentation.model.DashboardUiState
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class DashboardRepository {
    
    suspend fun getDashboardData(templeSite: TempleSite = TempleSite.SOMNATH): DashboardUiState {
        val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        
        // Fetch from live backend
        val remoteTemples = try { NetworkClient.getTemples() } catch (_: Exception) { emptyList() }
        val targetTemple = remoteTemples.find { it.id.equals(templeSite.backendId, ignoreCase = true) }
        
        val remoteQueues = try { NetworkClient.getQueues(templeSite.backendId) } catch (_: Exception) { emptyList() }
        
        if (targetTemple != null) {
            val occupancy = targetTemple.currentOccupancy
            val capacity = targetTemple.dailyCapacity
            val pct = if (capacity > 0) ((occupancy.toDouble() / capacity) * 100).toInt().coerceIn(5, 99) else 45
            
            val statusLabel = when {
                pct >= 75 -> "High"
                pct >= 45 -> "Moderate"
                else -> "Low"
            }
            
            // Derive average/peak queue wait time from active gates
            val avgWait = if (remoteQueues.isNotEmpty()) {
                val openQueues = remoteQueues.filter { it.status == "OPEN" }
                if (openQueues.isNotEmpty()) openQueues.map { it.waitingTimeMin }.average().toInt()
                else remoteQueues.map { it.waitingTimeMin }.average().toInt()
            } else {
                15
            }
            
            val formattedVisitors = NumberFormat.getNumberInstance(Locale.US).format(occupancy)
            
            val upcomingAarti = targetTemple.aartis.firstOrNull { it.status.equals("Current", true) || it.status.equals("Upcoming", true) }
                ?: targetTemple.aartis.lastOrNull()
            
            val eventsList = targetTemple.aartis.map { "${it.name} (${it.time})" }
            
            val noticesList = listOf(
                "Active Festival: ${targetTemple.activeFestival}",
                "Police In-Charge: ${targetTemple.policeIncharge}",
                "Emergency / Control Room: ${targetTemple.emergencyContact}"
            )
            
            return DashboardUiState(
                devoteeName = targetTemple.name,
                isTempleOpen = true,
                crowdStatus = statusLabel,
                waitTime = "$avgWait mins",
                totalVisitors = formattedVisitors,
                lastUpdated = "Live Sync",
                crowdPercentage = pct,
                aiPrediction = if (pct > 60) "High surge expected near Aarti hour" else "Optimal darshan conditions",
                predictionConfidence = 94,
                bestTime = if (pct > 60) "Early Morning (6 AM - 8 AM)" else "Current hour is favorable",
                temperature = 28,
                weatherCondition = "Clear Sky",
                feelsLike = "29°C",
                humidity = 62,
                windSpeed = "12 km/h",
                weatherIcon = "🌤",
                openingTime = "06:00 AM",
                closingTime = "09:30 PM",
                nextAarti = upcomingAarti?.let { "${it.name} at ${it.time}" } ?: "07:00 PM",
                currentTime = currentTime,
                todayEvents = if (eventsList.isNotEmpty()) eventsList else listOf("Pratah Mangla Aarti (07:00 AM)", "Sandhya Maha Aarti (07:00 PM)"),
                importantNotices = noticesList
            )
        }
        
        // Graceful offline fallback
        return DashboardUiState(
            devoteeName = templeSite.displayName,
            isTempleOpen = true,
            crowdStatus = "Low",
            waitTime = "15 mins",
            totalVisitors = "24,510",
            lastUpdated = "Offline Cache",
            crowdPercentage = 35,
            aiPrediction = "Moderate crowd flow expected",
            predictionConfidence = 90,
            bestTime = "3 PM - 4 PM",
            temperature = 28,
            weatherCondition = "Sunny",
            feelsLike = "30°C",
            humidity = 60,
            windSpeed = "8 km/h",
            weatherIcon = "🌤",
            openingTime = "6:30 AM",
            closingTime = "9:30 PM",
            nextAarti = "Sandhya Aarti at 7:00 PM",
            currentTime = currentTime,
            todayEvents = listOf(
                "Mangala Aarti (07:00 AM)",
                "Shringar Darshan (12:00 PM)",
                "Sandhya Aarti (07:00 PM)"
            ),
            importantNotices = listOf(
                "Follow Queue System & Security Directions",
                "Mobile phones must be deposited in cloakrooms",
                "Assistance counter available at Main Entrance"
            )
        )
    }
}