package com.temple.crowdmanagement.features.booking.data

import com.temple.crowdmanagement.core.model.BookingPass
import com.temple.crowdmanagement.core.model.DarshanSlot
import com.temple.crowdmanagement.core.model.TempleSite
import com.temple.crowdmanagement.core.network.NetworkClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookingRepository(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {

    private val _userBookings = MutableStateFlow<List<BookingPass>>(
        listOf(
            BookingPass(
                bookingId = "TC-SMN982",
                temple = TempleSite.SOMNATH,
                slotTime = "07:00 AM - 08:00 AM",
                devoteeCount = 2,
                passHolderName = "Ramesh Kumar",
                status = "ACTIVE"
            )
        )
    )
    val userBookings: StateFlow<List<BookingPass>> = _userBookings.asStateFlow()

    fun getAvailableSlots(temple: TempleSite): List<DarshanSlot> {
        return listOf(
            DarshanSlot("S1", temple, "06:00 AM - 07:00 AM (Morning Aarti)", 500, 480, isAartiSpecial = true),
            DarshanSlot("S2", temple, "07:30 AM - 08:30 AM", 800, 320),
            DarshanSlot("S3", temple, "09:00 AM - 10:00 AM", 800, 650),
            DarshanSlot("S4", temple, "11:00 AM - 12:00 PM (Midday Darshan)", 1000, 990),
            DarshanSlot("S5", temple, "04:00 PM - 05:00 PM", 800, 210),
            DarshanSlot("S6", temple, "07:00 PM - 08:00 PM (Evening Sandhya Aarti)", 600, 595, isAartiSpecial = true)
        )
    }

    /**
     * Confirm a booking locally AND sync to the bridge server so the web
     * admin Booking Operations page reflects it in real-time.
     */
    fun confirmBooking(temple: TempleSite, slotTime: String, devoteeCount: Int, name: String): BookingPass {
        val newPass = BookingPass(
            temple = temple,
            slotTime = slotTime,
            devoteeCount = devoteeCount,
            passHolderName = name.ifBlank { "Pilgrim Devotee" }
        )
        _userBookings.value = listOf(newPass) + _userBookings.value

        // 🔄 Sync to bridge server → web admin sees it instantly
        scope.launch(Dispatchers.IO) {
            try {
                NetworkClient.postBooking(
                    templeId = temple.backendId,
                    slotTime = slotTime,
                    devoteeCount = devoteeCount,
                    name = newPass.passHolderName
                )
            } catch (_: Exception) {
                // Silent — offline bookings still work
            }
        }

        return newPass
    }
}
