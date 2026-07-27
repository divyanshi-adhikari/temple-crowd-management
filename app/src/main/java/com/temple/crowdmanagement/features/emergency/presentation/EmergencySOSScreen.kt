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
            .padding(16.dp)
    ) {
        // Header
        Column {
            Text(
                text = "Emergency & SOS",
                style = MaterialTheme.typography.headlineLarge,
                color = StatusRed
            )
            Text(
                text = "Somnath Temple · Instant First Responder Alerting",
                color = TextSecondary,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Offline Network Toggle Simulation Card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardDarkBg, RoundedCornerShape(12.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (isOfflineMode) Icons.Default.WifiOff else Icons.Default.Wifi,
                    contentDescription = null,
                    tint = if (isOfflineMode) StatusOrange else StatusGreen
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = if (isOfflineMode) "Network Offline (Mesh Protocol Active)" else "Network Online (Cloud Dispatch)",
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        fontSize = 13.sp
                    )
                    Text(
                        text = if (isOfflineMode) "SOS will broadcast via BLE peer-to-peer mesh" else "Direct high-speed cellular relay",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }
            }
            Switch(
                checked = isOfflineMode,
                onCheckedChange = { isOfflineMode = it },
                colors = SwitchDefaults.colors(checkedThumbColor = StatusOrange)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // Panic SOS Big Button Item
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardDarkBg),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, StatusRed.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "PRESS FOR PANIC SOS",
                            fontWeight = FontWeight.Bold,
                            color = StatusRed,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // Large Panic Circle
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

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Dispatches GPS location & alerts nearby police & medical teams",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Quick Category SOS Triggers
            item {
                Text(
                    text = "Specific Category Emergency Triggers",
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary
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
                        color = SandstoneGold,
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
                        style = MaterialTheme.typography.titleLarge,
                        color = GoldAccent
                    )
                }

                items(alerts) { alert ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = SurfaceVariantDark),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(alert.id, fontWeight = FontWeight.Bold, color = StatusRed)
                                Text(alert.timestamp, color = TextSecondary, fontSize = 11.sp)
                            }
                            Text(alert.alertType, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text(alert.location, color = TextSecondary, fontSize = 11.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Surface(
                                color = if (alert.isOfflineMesh) StatusOrange.copy(alpha = 0.2f) else StatusGreen.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    alert.status,
                                    color = if (alert.isOfflineMesh) StatusOrange else StatusGreen,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
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
                    Text("EMERGENCY SIGNAL BROADCAST!", color = StatusRed, fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column {
                    Text("Alert Type: ${alert.alertType}", fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text("Location: ${alert.location}", color = TextSecondary, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Status: ${alert.status}", color = SandstoneGold, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                    if (alert.isOfflineMesh) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "📱 Offline Mesh Mode: Signal broadcasting to nearest crowd marshal's phone via Bluetooth Low Energy packet relay.",
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
                    Text("ACKNOWLEDGE & STAY CALM")
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
            .border(1.dp, color.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .clickable { onClick() }
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
