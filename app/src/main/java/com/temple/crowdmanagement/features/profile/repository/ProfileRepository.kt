package com.temple.crowdmanagement.features.profile.repository

import com.temple.crowdmanagement.features.profile.model.ProfileData
import com.temple.crowdmanagement.features.profile.model.RecentBooking
import com.temple.crowdmanagement.features.profile.model.BookingStatus
import com.temple.crowdmanagement.features.profile.model.VisitPreferences
import com.temple.crowdmanagement.features.profile.model.Settings
import kotlinx.coroutines.delay

class ProfileRepository {
    
    suspend fun getProfileData(): ProfileData {
        delay(600)
        
        return ProfileData(
            userName = "Demo User",
            userRole = "Temple Devotee Member",
            userEmail = "demo@temple.com",
            recentBookings = listOf(
                RecentBooking(
                    id = "1",
                    type = "VIP Darshan",
                    date = "24 Jul 2026",
                    slot = "Morning Slot",
                    status = BookingStatus.COMPLETED
                ),
                RecentBooking(
                    id = "2",
                    type = "Special Aarti",
                    date = "15 Jul 2026",
                    slot = "Evening Slot",
                    status = BookingStatus.COMPLETED
                ),
                RecentBooking(
                    id = "3",
                    type = "Abhishek",
                    date = "30 Jul 2026",
                    slot = "Morning Slot",
                    status = BookingStatus.UPCOMING
                )
            ),
            visitPreferences = VisitPreferences(
                lastVisit = "24 Jul 2026",
                preferredDarshan = "VIP Morning",
                favoriteFestival = "Janmashtami",
                preferredLanguage = "English"
            ),
            settings = Settings(
                language = "English",
                alerts = true,
                theme = "Auto"
            )
        )
    }
}