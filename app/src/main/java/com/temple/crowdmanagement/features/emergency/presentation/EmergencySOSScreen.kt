package com.temple.crowdmanagement.features.emergency.presentation

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.temple.crowdmanagement.core.model.EmergencyAlert
import com.temple.crowdmanagement.features.emergency.data.OfflineMeshEngine
import com.temple.crowdmanagement.ui.theme.*

@Composable
fun EmergencySOSScreen(
    engine: OfflineMeshEngine = remember { OfflineMeshEngine() }
) {
    val alerts by engine.recentAlerts.collectAsState()
    var isOfflineMode by remember { mutableStateOf(false) }
    var activeTriggerAlert by remember { mutableStateOf<EmergencyAlert?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpiritualDarkBg)
    ) {
        // Maroon header — emergency screens share the same brand header style
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(SaffronDark, SaffronPrimary)
                    )
                )
                .padding(top = 48.dp, bottom = 14.dp, start = 20.dp, end = 20.dp)
        ) {
            Column {
                Text(
                    text = "Emergency & SOS",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Instant first responder dispatch protocol",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.75f)
                )
            }
        }

        // Single flat scrollable LazyColumn to avoid scroll nesting crash
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Offline Network Toggle Simulation Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardDarkBg),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = if (isOfflineMode) Icons.Default.WifiOff else Icons.Default.Wifi,
                                contentDescription = null,
                                tint = if (isOfflineMode) StatusOrange else StatusGreen,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = if (isOfflineMode) "Network Offline (Mesh Active)" else "Network Online (Cloud Dispatch)",
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = if (isOfflineMode) "SOS broadcasts via P2P Bluetooth mesh" else "Direct high-speed cellular relay",
                                    color = TextSecondary,
                                    fontSize = 11.sp
                                )
                            }
                        }
                        Switch(
                            checked = isOfflineMode,
                            onCheckedChange = { isOfflineMode = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = StatusOrange,
                                uncheckedThumbColor = TextSecondary,
                                uncheckedTrackColor = SurfaceVariantDark
                            )
                        )
                    }
                }
            }

            // Panic SOS Big Button Item (Kept large, round, and red)
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardDarkBg),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, StatusRed.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "PRESS FOR PANIC SOS",
                            fontWeight = FontWeight.Bold,
                            color = StatusRed,
                            fontSize = 15.sp,
                            letterSpacing = 0.5.sp
                        )
                        Spacer(modifier = Modifier.height(20.dp))

                        // Large Panic Circle Button
                        Box(
                            modifier = Modifier
                                .size(140.dp)
                                .background(StatusRed, CircleShape)
                                .border(4.dp, Color.White.copy(alpha = 0.8f), CircleShape)
                                .clickable {
                                    activeTriggerAlert = engine.triggerSOS("GENERAL PANIC SOS", !isOfflineMode)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    Icons.Default.Sos,
                                    contentDescription = "SOS",
                                    tint = Color.White,
                                    modifier = Modifier.size(56.dp)
                                )
                                Text(
                                    "TAP NOW",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Dispatches GPS location & alerts nearby police & medical teams",
                            color = TextSecondary,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 8.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }

            // Quick Category SOS Triggers
            item {
                Text(
                    text = "Specific Category Emergency Triggers",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.padding(top = 4.dp, bottom = 2.dp)
                )
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CategorySosCard(
                        title = "Medical",
                        icon = Icons.Default.MedicalServices,
                        color = StatusRed,
                        modifier = Modifier.weight(1f),
                        onClick = { activeTriggerAlert = engine.triggerSOS("MEDICAL EMERGENCY", !isOfflineMode) }
                    )
                    CategorySosCard(
                        title = "Stampede",
                        icon = Icons.Default.Groups,
                        color = StatusOrange,
                        modifier = Modifier.weight(1f),
                        onClick = { activeTriggerAlert = engine.triggerSOS("CROWD STAMPEDE RISK", !isOfflineMode) }
                    )
                }
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CategorySosCard(
                        title = "Fire Risk",
                        icon = Icons.Default.LocalFireDepartment,
                        color = Color(0xFFD84315),
                        modifier = Modifier.weight(1f),
                        onClick = { activeTriggerAlert = engine.triggerSOS("FIRE HAZARD", !isOfflineMode) }
                    )
                    CategorySosCard(
                        title = "Lost Person",
                        icon = Icons.Default.PersonSearch,
                        color = GoldAccent,
                        modifier = Modifier.weight(1f),
                        onClick = { activeTriggerAlert = engine.triggerSOS("LOST CHILD / ELDERLY", !isOfflineMode) }
                    )
                }
            }

            // Recent Alerts Log
            if (alerts.isNotEmpty()) {
                item {
                    Text(
                        text = "Dispatched Emergency Log",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                items(alerts) { alert ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CardDarkBg),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(alert.id, fontWeight = FontWeight.Bold, color = StatusRed, fontSize = 13.sp)
                                Text(alert.timestamp, color = TextSecondary, fontSize = 11.sp)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(alert.alertType, fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 13.sp)
                            Text(alert.location, color = TextSecondary, fontSize = 11.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                color = if (alert.isOfflineMesh) StatusOrange.copy(alpha = 0.15f) else StatusGreen.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    alert.status,
                                    color = if (alert.isOfflineMesh) StatusOrange else StatusGreen,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Alert Dispatched Modal
    activeTriggerAlert?.let { alert ->
        AlertDialog(
            onDismissRequest = { activeTriggerAlert = null },
            containerColor = CardDarkBg,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = StatusRed)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("EMERGENCY SIGNAL SENT", color = StatusRed, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            },
            text = {
                Column {
                    Text("Alert Type: ${alert.alertType}", fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text("Location: ${alert.location}", color = TextSecondary, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Status: ${alert.status}", color = GoldAccent, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                    if (alert.isOfflineMesh) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "📱 Offline Mesh Mode: Signal broadcasting to nearest crowd marshal's phone via BLE P2P relay.",
                            color = StatusOrange,
                            fontSize = 11.sp
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { activeTriggerAlert = null },
                    colors = ButtonDefaults.buttonColors(containerColor = StatusRed)
                ) {
                    Text("ACKNOWLEDGE & STAY CALM", color = Color.White)
                }
            }
        )
    }
}

@Composable
fun CategorySosCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CardDarkBg),
        modifier = modifier
            .border(1.dp, color.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = title, tint = color, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 13.sp)
        }
    }
}
