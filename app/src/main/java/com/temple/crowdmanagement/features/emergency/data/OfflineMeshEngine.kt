package com.temple.crowdmanagement.features.emergency.data

import com.temple.crowdmanagement.core.model.EmergencyAlert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

class OfflineMeshEngine {

    private val _recentAlerts = MutableStateFlow<List<EmergencyAlert>>(emptyList())
    val recentAlerts: StateFlow<List<EmergencyAlert>> = _recentAlerts.asStateFlow()

    fun triggerSOS(alertType: String, isNetworkAvailable: Boolean): EmergencyAlert {
        val sdf = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
        val currentTime = sdf.format(Date())

        val alert = EmergencyAlert(
            id = "SOS-" + Random().nextInt(9000) + 1000,
            alertType = alertType,
            location = "GPS: 20.8880° N, 70.4012° E (Somnath Sanctum East)",
            timestamp = currentTime,
            isOfflineMesh = !isNetworkAvailable,
            status = if (!isNetworkAvailable) "BROADCASTING VIA BLE MESH" else "ALERT DISPATCHED TO FIRST RESPONDERS"
        )

        _recentAlerts.value = listOf(alert) + _recentAlerts.value
        return alert
    }
}
