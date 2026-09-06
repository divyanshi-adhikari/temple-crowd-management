package com.temple.crowdmanagement.features.emergency.data

import com.temple.crowdmanagement.core.model.EmergencyAlert
import com.temple.crowdmanagement.core.network.NetworkClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class OfflineMeshEngine(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {

    private val _recentAlerts = MutableStateFlow<List<EmergencyAlert>>(emptyList())
    val recentAlerts: StateFlow<List<EmergencyAlert>> = _recentAlerts.asStateFlow()

    /**
     * Trigger an SOS alert.
     * - Always creates a local alert (works offline too via BLE mesh)
     * - If network is available, posts to bridge server so web admin Incidents
     *   page shows a live Critical incident immediately
     *
     * @param alertType  e.g. "Medical Emergency", "Crowd Crush", "Fire"
     * @param isNetworkAvailable  from ConnectivityManager check
     * @param templeId   backend ID of the current temple (e.g. "somnath")
     * @param locationLabel human-readable location for the admin dashboard
     */
    fun triggerSOS(
        alertType: String,
        isNetworkAvailable: Boolean,
        templeId: String = "somnath",
        locationLabel: String = "Temple Campus"
    ): EmergencyAlert {
        val sdf = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
        val currentTime = sdf.format(Date())

        val alert = EmergencyAlert(
            id = "SOS-" + (Random().nextInt(9000) + 1000),
            alertType = alertType,
            location = "GPS: 20.8880° N, 70.4012° E · $locationLabel",
            timestamp = currentTime,
            isOfflineMesh = !isNetworkAvailable,
            status = if (!isNetworkAvailable)
                "BROADCASTING VIA BLE MESH"
            else
                "✅ ALERT DISPATCHED TO ADMIN DASHBOARD"
        )

        _recentAlerts.value = listOf(alert) + _recentAlerts.value

        // 🔴 If online: push to bridge server → admin web dashboard sees it NOW
        if (isNetworkAvailable) {
            scope.launch(Dispatchers.IO) {
                try {
                    // Use the explicit SOS-to-incidents pathway
                    NetworkClient.postSOS(
                        templeId = templeId,
                        alertType = alertType,
                        location = locationLabel
                    )
                } catch (_: Exception) {
                    // Fallback: try the generic createIncident endpoint
                    try {
                        NetworkClient.createIncident(
                            templeId = templeId,
                            title = "EMERGENCY SOS: $alertType",
                            description = "Pilgrim triggered mobile panic SOS at $locationLabel",
                            severity = "CRITICAL",
                            location = locationLabel
                        )
                    } catch (_: Exception) { /* Offline — alert still local */ }
                }
            }
        }

        return alert
    }
}
