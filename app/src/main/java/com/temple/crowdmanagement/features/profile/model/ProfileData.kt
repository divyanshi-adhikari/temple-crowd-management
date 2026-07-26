package com.temple.crowdmanagement.features.profile.model

data class ProfileData(
    val userName: String = "Demo User",
    val userRole: String = "Temple Devotee Member",
    val userEmail: String = "demo@temple.com",
    val profileImage: String = "",
    val recentBookings: List<RecentBooking> = emptyList(),
    val visitPreferences: VisitPreferences = VisitPreferences(),
    val settings: Settings = Settings()
)

data class RecentBooking(
    val id: String,
    val type: String,
    val date: String,
    val slot: String,
    val status: BookingStatus,
    val templeName: String = "Dwarkadhish Temple"
)

enum class BookingStatus {
    COMPLETED,
    UPCOMING,
    CANCELLED
}

data class VisitPreferences(
    val lastVisit: String = "24 Jul 2026",
    val preferredDarshan: String = "VIP Morning",
    val favoriteFestival: String = "Maha Shivratri",
    val preferredLanguage: String = "English"
)

data class Settings(
    val language: String = "English",
    val alerts: Boolean = true,
    val theme: String = "Auto"
)