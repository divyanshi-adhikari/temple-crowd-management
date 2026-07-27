package com.temple.crowdmanagement.core.model

import androidx.compose.ui.graphics.Color
import java.util.UUID

enum class TempleSite(val displayName: String, val location: String, val imageTag: String) {
    SOMNATH("Somnath Temple", "Veraval, Gujarat", "somnath")
}

data class DarshanSlot(
    val slotId: String,
    val temple: TempleSite,
    val timeLabel: String,
    val maxCapacity: Int,
    val bookedCount: Int,
    val isAartiSpecial: Boolean = false
) {
    val isAvailable: Boolean get() = bookedCount < maxCapacity
    val percentageFull: Int get() = ((bookedCount.toFloat() / maxCapacity) * 100).toInt()
}

data class BookingPass(
    val bookingId: String = "TC-" + UUID.randomUUID().toString().take(6).uppercase(),
    val temple: TempleSite,
    val slotTime: String,
    val devoteeCount: Int,
    val passHolderName: String = "Pilgrim Devotee",
    val qrCodePayload: String = "PASS-$bookingId",
    val status: String = "CONFIRMED"
)

enum class CrowdDensityLevel(val label: String, val colorHex: Long) {
    SMOOTH("Low Crowd", 0xFF2E7D32),
    MODERATE("Moderate Crowd", 0xFFEF6C00),
    HEAVY("Heavy Surge", 0xFFC62828),
    CRITICAL("Extreme Overcrowding", 0xFFB71C1C)
}

data class ZonePOI(
    val id: String,
    val name: String,
    val type: POIType,
    val xPercent: Float,
    val yPercent: Float,
    val details: String
)

enum class POIType {
    ENTRY_GATE, EXIT_GATE, PARKING, WASHROOM, MEDICAL, WATER, SECURITY
}

data class HeatZone(
    val zoneName: String,
    val density: CrowdDensityLevel,
    val currentCount: Int,
    val maxCapacity: Int,
    val relativeX: Float,
    val relativeY: Float,
    val radiusRatio: Float
)

data class QueueState(
    val activeToken: String,
    val temple: TempleSite,
    val currentPosition: Int,
    val estimatedWaitMinutes: Int,
    val isNotifyEnabled: Boolean = false,
    val joinedTime: String = "10:30 AM"
)

data class EmergencyAlert(
    val id: String,
    val alertType: String,
    val location: String,
    val timestamp: String,
    val isOfflineMesh: Boolean = false,
    val status: String = "DISPATCHED"
)
