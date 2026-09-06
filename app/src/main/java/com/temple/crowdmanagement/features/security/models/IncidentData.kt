package com.temple.crowdmanagement.features.security.model

enum class IncidentType(val displayName: String, val icon: String) {
    SECURITY("Security", "🔒"),
    MEDICAL("Medical", "🏥"),
    CROWD("Crowd", "👥"),
    EMERGENCY("Emergency", "🚨"),
    FIRE("Fire", "🔥"),
    THEFT("Theft", "💰"),          // ✅ Added
    ACCIDENT("Accident", "🚗"),    // ✅ Added
    OTHER("Other", "📌")
}

enum class IncidentPriority(val displayName: String, val color: String) {
    LOW("Low", "#8BC34A"),
    MEDIUM("Medium", "#FFC107"),
    HIGH("High", "#FF9800"),
    CRITICAL("Critical", "#F44336"),
    URGENT("Urgent", "#9C27B0")    // ✅ Added
}

data class IncidentReport(
    val id: String = "",
    val type: IncidentType,
    val priority: IncidentPriority,
    val title: String,
    val description: String,
    val location: String,
    val reportedBy: String,
    val timestamp: String = "",
    val status: String = "Pending"
)