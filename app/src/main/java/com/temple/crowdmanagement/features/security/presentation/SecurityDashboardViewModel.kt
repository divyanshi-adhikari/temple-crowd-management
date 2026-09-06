package com.temple.crowdmanagement.features.security.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.temple.crowdmanagement.features.security.model.DutyInfo
import com.temple.crowdmanagement.features.security.model.DutyStatus
import com.temple.crowdmanagement.features.security.model.IncidentReport
import com.temple.crowdmanagement.features.security.model.IncidentType
import com.temple.crowdmanagement.features.security.model.IncidentPriority
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SecurityDashboardViewModel : ViewModel() {
    
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _securityName = MutableStateFlow("")
    val securityName: StateFlow<String> = _securityName.asStateFlow()
    
    private val _templeId = MutableStateFlow("")
    val templeId: StateFlow<String> = _templeId.asStateFlow()
    
    private val _badgeId = MutableStateFlow("")
    val badgeId: StateFlow<String> = _badgeId.asStateFlow()
    
    private val _pendingAlerts = MutableStateFlow<List<AlertData>>(emptyList())
    val pendingAlerts: StateFlow<List<AlertData>> = _pendingAlerts.asStateFlow()
    
    // ✅ Duty Status
    private val _dutyInfo = MutableStateFlow(DutyInfo())
    val dutyInfo: StateFlow<DutyInfo> = _dutyInfo.asStateFlow()
    
    // ✅ Incident Reports
    private val _incidentReports = MutableStateFlow<List<IncidentReport>>(emptyList())
    val incidentReports: StateFlow<List<IncidentReport>> = _incidentReports.asStateFlow()

    fun loadSecurityData() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(800)
            
            _securityName.value = "Security Officer 1"
            _templeId.value = "Somnath Temple"
            _badgeId.value = "SEC001"
            
            _pendingAlerts.value = listOf(
                AlertData(
                    id = "1",
                    title = "Suspicious Activity Detected",
                    message = "Unauthorized person found near the main entrance. Please investigate immediately.",
                    type = "security",
                    priority = "high",
                    templeId = "somnath",
                    location = "Main Entrance Gate",
                    createdAt = "10:30 AM",
                    acknowledged = false
                ),
                AlertData(
                    id = "2",
                    title = "Medical Assistance Required",
                    message = "A devotee needs medical assistance near the queue area.",
                    type = "medical",
                    priority = "medium",
                    templeId = "somnath",
                    location = "Queue Area",
                    createdAt = "09:45 AM",
                    acknowledged = false
                )
            )
            
            _isLoading.value = false
        }
    }

    fun acknowledgeAlert(alertId: String) {
        viewModelScope.launch {
            _pendingAlerts.value = _pendingAlerts.value.map { alert ->
                if (alert.id == alertId) {
                    alert.copy(acknowledged = true)
                } else {
                    alert
                }
            }
        }
    }
    
    // ✅ Update Duty Status
    fun updateDutyStatus(status: DutyStatus) {
        _dutyInfo.value = _dutyInfo.value.copy(status = status)
    }
    
    // ✅ Report Incident
    fun reportIncident(
        type: IncidentType,
        priority: IncidentPriority,
        title: String,
        description: String,
        location: String
    ) {
        viewModelScope.launch {
            val timestamp = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
            
            val report = IncidentReport(
                id = UUID.randomUUID().toString().take(8),
                type = type,
                priority = priority,
                title = title,
                description = description,
                location = location,
                reportedBy = _securityName.value,
                timestamp = timestamp
            )
            
            _incidentReports.value = listOf(report) + _incidentReports.value
            
            // Simulate sending to backend
            delay(500)
        }
    }
    
    // ✅ Send Message to Controller
    fun sendMessageToController(message: String) {
        viewModelScope.launch {
            // TODO: Send to backend
            delay(500)
        }
    }
    
    // ✅ Call Controller
    fun callController() {
        // TODO: Trigger phone call
    }
    
    // ✅ Trigger Emergency
    fun triggerEmergency() {
        viewModelScope.launch {
            val timestamp = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
            
            // Add emergency alert
            val emergencyAlert = AlertData(
                id = UUID.randomUUID().toString().take(8),
                title = "🚨 EMERGENCY ALERT",
                message = "Security Officer 1 has triggered an emergency alert!",
                type = "emergency",
                priority = "high",
                templeId = "somnath",
                location = _dutyInfo.value.assignedZone,
                createdAt = timestamp,
                acknowledged = false
            )
            _pendingAlerts.value = listOf(emergencyAlert) + _pendingAlerts.value
            
            // TODO: Send to backend
            delay(500)
        }
    }
}