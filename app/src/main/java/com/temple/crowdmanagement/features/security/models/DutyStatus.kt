package com.temple.crowdmanagement.features.security.model

enum class DutyStatus(val displayName: String, val emoji: String, val color: String) {
    ON_DUTY("On Duty", "🟢", "#4CAF50"),
    ON_BREAK("On Break", "🟡", "#FFC107"),
    OFF_DUTY("Off Duty", "🔴", "#F44336")
}

data class DutyInfo(
    val status: DutyStatus = DutyStatus.ON_DUTY,
    val shiftStart: String = "08:00 AM",
    val shiftEnd: String = "04:00 PM",
    val assignedZone: String = "Main Entrance"
)