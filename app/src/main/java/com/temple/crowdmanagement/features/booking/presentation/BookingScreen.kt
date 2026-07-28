package com.temple.crowdmanagement.features.booking.presentation

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.temple.crowdmanagement.core.model.BookingPass
import com.temple.crowdmanagement.core.model.DarshanSlot
import com.temple.crowdmanagement.core.model.TempleSite
import com.temple.crowdmanagement.features.booking.data.BookingRepository
import com.temple.crowdmanagement.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    repository: BookingRepository = remember { BookingRepository() }
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Book Slot, 1 = My Passes
    val userPasses by repository.userBookings.collectAsState()

    var selectedSlot by remember { mutableStateOf<DarshanSlot?>(null) }
    var devoteeCount by remember { mutableIntStateOf(1) }
    var devoteeName by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf<BookingPass?>(null) }

    val availableSlots = remember { repository.getAvailableSlots(TempleSite.SOMNATH) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpiritualDarkBg)
            .padding(16.dp)
    ) {
        // Screen Header
        Column {
            Text(
                text = "⚑  SOMNATH TEMPLE · DARSHAN BOOKING",
                color = SaffronPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Smart Darshan Booking",
                style = MaterialTheme.typography.headlineLarge,
                color = TextPrimary
            )
            Text(
                text = "Reserve your sacred Darshan slot",
                color = TextSecondary,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Spacer(modifier = Modifier.height(4.dp))

        // Toggle Tab: Book vs My Passes
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = SurfaceVariantDark,
            contentColor = TextPrimary
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Book Slot", fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("My Passes (${userPasses.size})", fontWeight = FontWeight.Bold) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedTab == 0) {
            // BOOK SLOT FLOW
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                item {
                    Text(
                        text = "Select Darshan Time Slot",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary
                    )
                }

                items(availableSlots) { slot ->
                    SlotItemCard(
                        slot = slot,
                        isSelected = selectedSlot == slot,
                        onSelect = { selectedSlot = slot }
                    )
                }

                if (selectedSlot != null) {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = CardDarkBg),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Devotee Details",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = GoldAccent
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = devoteeName,
                                    onValueChange = { devoteeName = it },
                                    label = { Text("Lead Devotee Name") },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = TextPrimary,
                                        unfocusedTextColor = TextPrimary,
                                        focusedBorderColor = SaffronPrimary,
                                        unfocusedBorderColor = TextSecondary
                                    )
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Number of Devotees:", color = TextPrimary)
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        IconButton(onClick = { if (devoteeCount > 1) devoteeCount-- }) {
                                            Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = TextPrimary)
                                        }
                                        Text(
                                            devoteeCount.toString(),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp,
                                            color = SandstoneGold
                                        )
                                        IconButton(onClick = { if (devoteeCount < 6) devoteeCount++ }) {
                                            Icon(Icons.Default.Add, contentDescription = "Increase", tint = TextPrimary)
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = {
                                        val confirmedPass = repository.confirmBooking(
                                            temple = TempleSite.SOMNATH,
                                            slotTime = selectedSlot!!.timeLabel,
                                            devoteeCount = devoteeCount,
                                            name = devoteeName
                                        )
                                        showSuccessDialog = confirmedPass
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(Icons.Default.ConfirmationNumber, contentDescription = null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("CONFIRM & GENERATE QR PASS", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // MY PASSES LIST
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(userPasses) { pass ->
                    PassCardItem(pass = pass)
                }
            }
        }
    }

    // Success QR Dialog
    showSuccessDialog?.let { pass ->
        AlertDialog(
            onDismissRequest = { showSuccessDialog = null },
            containerColor = CardDarkBg,
            title = {
                Text(
                    "Darshan Slot Booked Successfully! 🙏",
                    color = GoldAccent,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Pass ID: ${pass.bookingId}", color = TextPrimary, fontWeight = FontWeight.Bold)
                    Text(pass.temple.displayName, color = SandstoneGold)
                    Text("Slot: ${pass.slotTime}", color = TextSecondary, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(16.dp))

                    // QR Code Visual Simulation Box
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .border(2.dp, SaffronPrimary, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.QrCode2,
                                contentDescription = "QR Pass",
                                tint = Color.Black,
                                modifier = Modifier.size(120.dp)
                            )
                            Text(
                                pass.bookingId,
                                color = Color.Black,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = null
                        selectedTab = 1
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary)
                ) {
                    Text("VIEW IN MY PASSES")
                }
            }
        )
    }
}

@Composable
fun SlotItemCard(
    slot: DarshanSlot,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val borderColor = if (isSelected) SaffronPrimary else Color.Transparent
    val containerBg = if (isSelected) SurfaceVariantDark else CardDarkBg

    Card(
        colors = CardDefaults.cardColors(containerColor = containerBg),
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable { onSelect() }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = slot.timeLabel,
                    fontWeight = FontWeight.Bold,
                    color = if (slot.isAartiSpecial) GoldAccent else TextPrimary
                )
                if (slot.isAartiSpecial) {
                    Surface(
                        color = SandstoneGold.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            "AARTI",
                            color = SandstoneGold,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = slot.percentageFull / 100f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(CircleShape),
                color = if (slot.percentageFull > 90) StatusRed else StatusGreen,
                trackColor = SurfaceVariantDark
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${slot.bookedCount}/${slot.maxCapacity} Booked",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
                Text(
                    text = if (slot.isAvailable) "Available" else "FULL",
                    fontSize = 12.sp,
                    color = if (slot.isAvailable) StatusGreen else StatusRed,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun PassCardItem(pass: BookingPass) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CardDarkBg),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.QrCode,
                contentDescription = null,
                tint = GoldAccent,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(pass.bookingId, fontWeight = FontWeight.Bold, color = GoldAccent)
                Text(pass.temple.displayName, color = TextPrimary, fontSize = 14.sp)
                Text(pass.slotTime, color = TextSecondary, fontSize = 12.sp)
                Text("Devotees: ${pass.devoteeCount} | Holder: ${pass.passHolderName}", color = TextSecondary, fontSize = 11.sp)
            }
            Surface(
                color = StatusGreen.copy(alpha = 0.2f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    pass.status,
                    color = StatusGreen,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}
