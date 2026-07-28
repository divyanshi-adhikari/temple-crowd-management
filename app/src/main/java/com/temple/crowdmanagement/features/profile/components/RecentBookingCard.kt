package com.temple.crowdmanagement.features.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.temple.crowdmanagement.features.profile.model.RecentBooking
import com.temple.crowdmanagement.features.profile.model.BookingStatus
import com.temple.crowdmanagement.ui.theme.*

@Composable
fun RecentBookingCard(
    bookings: List<RecentBooking>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp), clip = false),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardDarkBg
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = SaffronPrimary.copy(alpha = 0.15f),
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            Icons.Default.QrCodeScanner,
                            contentDescription = "Bookings",
                            tint = SaffronPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Recent Bookings",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "View All",
                    fontSize = 12.sp,
                    color = SaffronPrimary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { /* Navigate to all bookings */ }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Booking List
            bookings.take(3).forEachIndexed { index, booking ->
                BookingItem(booking = booking)
                if (index != bookings.take(3).size - 1) {
                    Divider(
                        color = SurfaceVariantDark,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BookingItem(
    booking: RecentBooking
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = SaffronPrimary.copy(alpha = 0.1f),
            modifier = Modifier.size(36.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "🎫",
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Details
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = booking.type,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Text(
                text = "${booking.date} | ${booking.slot}",
                fontSize = 12.sp,
                color = TextSecondary
            )
        }

        // Status Badge
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = when (booking.status) {
                BookingStatus.COMPLETED -> SuccessGreen.copy(alpha = 0.15f)
                BookingStatus.UPCOMING -> SaffronPrimary.copy(alpha = 0.15f)
                BookingStatus.CANCELLED -> DangerRed.copy(alpha = 0.15f)
            }
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when (booking.status) {
                        BookingStatus.COMPLETED -> "✓"
                        BookingStatus.UPCOMING -> "🕒"
                        BookingStatus.CANCELLED -> "✗"
                    },
                    fontSize = 12.sp,
                    color = when (booking.status) {
                        BookingStatus.COMPLETED -> SuccessGreen
                        BookingStatus.UPCOMING -> SaffronPrimary
                        BookingStatus.CANCELLED -> DangerRed
                    }
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = booking.status.name,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = when (booking.status) {
                        BookingStatus.COMPLETED -> SuccessGreen
                        BookingStatus.UPCOMING -> SaffronPrimary
                        BookingStatus.CANCELLED -> DangerRed
                    }
                )
            }
        }
    }
}