package com.temple.crowdmanagement.features.security.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SecurityNotificationViewModel : ViewModel() {
    
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()
    
    val unreadCount: StateFlow<Int> = _notifications.map {
        it.count { !it.isRead }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = 0
    )

    fun loadNotifications() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(800)
            
            // Mock data - Replace with API call later
            _notifications.value = listOf(
                NotificationItem(
                    id = "1",
                    title = "🚨 Security Alert",
                    message = "Suspicious activity detected near the main entrance. Please investigate immediately.",
                    type = "security",
                    priority = "high",
                    timestamp = "10:30 AM - Today",
                    location = "Main Entrance",
                    isRead = false,
                    templeId = "somnath",
                    fromController = "Admin Controller"
                ),
                NotificationItem(
                    id = "2",
                    title = "🏥 Medical Assistance",
                    message = "A devotee requires immediate medical attention near the queue area.",
                    type = "medical",
                    priority = "medium",
                    timestamp = "09:45 AM - Today",
                    location = "Queue Area",
                    isRead = false,
                    templeId = "somnath",
                    fromController = "Admin Controller"
                ),
                NotificationItem(
                    id = "3",
                    title = "📢 Crowd Update",
                    message = "Crowd levels are increasing. Please monitor the queue area.",
                    type = "info",
                    priority = "medium",
                    timestamp = "08:30 AM - Today",
                    location = "Queue Area",
                    isRead = true,
                    templeId = "somnath"
                ),
                NotificationItem(
                    id = "4",
                    title = "✅ Shift Change",
                    message = "Your shift has been extended by 2 hours.",
                    type = "info",
                    priority = "low",
                    timestamp = "07:00 AM - Today",
                    location = null,
                    isRead = true,
                    templeId = "somnath"
                ),
                NotificationItem(
                    id = "5",
                    title = "🚨 Emergency Alert",
                    message = "Emergency situation reported at the main temple hall.",
                    type = "emergency",
                    priority = "high",
                    timestamp = "06:15 AM - Today",
                    location = "Main Temple Hall",
                    isRead = false,
                    templeId = "somnath",
                    fromController = "Emergency Control"
                )
            )
            
            _isLoading.value = false
        }
    }

    fun acknowledgeNotification(notificationId: String) {
        viewModelScope.launch {
            _notifications.value = _notifications.value.map { notification ->
                if (notification.id == notificationId) {
                    notification.copy(isRead = true)
                } else {
                    notification
                }
            }
        }
    }

    fun dismissNotification(notificationId: String) {
        viewModelScope.launch {
            _notifications.value = _notifications.value.filter { it.id != notificationId }
        }
    }

    fun markAllAsRead() {
        viewModelScope.launch {
            _notifications.value = _notifications.value.map { notification ->
                notification.copy(isRead = true)
            }
        }
    }
}