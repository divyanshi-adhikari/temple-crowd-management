package com.temple.crowdmanagement.features.queue.presentation

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.temple.crowdmanagement.core.model.TempleSite
import com.temple.crowdmanagement.features.queue.data.QueueEngine
import com.temple.crowdmanagement.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartQueueScreen(
    engine: QueueEngine = remember { QueueEngine() }
) {
    val queueState by engine.activeQueue.collectAsState()
    var selectedTemple by remember { mutableStateOf(TempleSite.SOMNATH) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpiritualDarkBg)
            .padding(16.dp)
    ) {
        // Workspace Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Smart Virtual Queue",
                    style = MaterialTheme.typography.headlineLarge,
                    color = GoldAccent
                )
                Text(
                    text = "Zero-Wait Queue Management",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
            Surface(
                color = SaffronPrimary.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "DEV 2 WORKSPACE",
                    color = SandstoneGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                if (queueState == null) {
                    // JOIN QUEUE CARD
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CardDarkBg),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                text = "Join Virtual Queue",
                                style = MaterialTheme.typography.titleLarge,
                                color = GoldAccent
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Reserve your spot virtually without standing in physical lines. Enjoy campus facilities until your turn arrives.",
                                color = TextSecondary,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            Text("Select Target Temple:", color = TextPrimary, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            ScrollableTabRow(
                                selectedTabIndex = selectedTemple.ordinal,
                                containerColor = SurfaceVariantDark,
                                contentColor = SandstoneGold,
                                edgePadding = 0.dp
                            ) {
                                TempleSite.values().forEach { temple ->
                                    Tab(
                                        selected = selectedTemple == temple,
                                        onClick = { selectedTemple = temple },
                                        text = { Text(temple.displayName, fontSize = 12.sp) }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Button(
                                onClick = { engine.joinQueue(selectedTemple) },
                                colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.ConfirmationNumber, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("GET VIRTUAL QUEUE TOKEN", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                } else {
                    // ACTIVE QUEUE LIVE DASHBOARD
                    val active = queueState!!
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CardDarkBg),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(2.dp, SaffronPrimary, RoundedCornerShape(16.dp))
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Surface(
                                color = SaffronPrimary.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "ACTIVE VIRTUAL TOKEN",
                                    color = SandstoneGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = active.activeToken,
                                fontSize = 36.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent
                            )
                            Text(
                                text = active.temple.displayName,
                                color = TextPrimary,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                QueueStatTile(
                                    label = "Queue Position",
                                    value = "#${active.currentPosition}",
                                    subtext = "devotees ahead",
                                    color = StatusOrange
                                )
                                QueueStatTile(
                                    label = "Estimated Wait",
                                    value = "${active.estimatedWaitMinutes}m",
                                    subtext = "approx. duration",
                                    color = StatusGreen
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Notify Me Engine Switch Card
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(SurfaceVariantDark, RoundedCornerShape(12.dp))
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.NotificationsActive,
                                        contentDescription = null,
                                        tint = GoldAccent
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text("Notify Me Engine", fontWeight = FontWeight.Bold, color = TextPrimary)
                                        Text("Vibrate & push when 5 mins away", color = TextSecondary, fontSize = 11.sp)
                                    }
                                }
                                Switch(
                                    checked = active.isNotifyEnabled,
                                    onCheckedChange = { engine.toggleNotification(it) },
                                    colors = SwitchDefaults.colors(checkedThumbColor = SaffronPrimary)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedButton(
                                    onClick = { engine.simulateQueueProgress() },
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = SandstoneGold),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(Icons.Default.FastForward, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("SIMULATE STEP", fontSize = 11.sp)
                                }
                                Button(
                                    onClick = { engine.leaveQueue() },
                                    colors = ButtonDefaults.buttonColors(containerColor = StatusRed),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("LEAVE QUEUE", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }

            item {
                // Queue Metrics Analytics Widget
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardDarkBg),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Live Queue Flow Analytics",
                            style = MaterialTheme.typography.titleLarge,
                            color = GoldAccent
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        MetricRow("Current Flow Rate:", "145 Devotees / 10 mins")
                        MetricRow("Gate 1 Clearance:", "Normal (45 sec / batch)")
                        MetricRow("Crowd Density Index:", "Moderate (42% capacity)")
                    }
                }
            }
        }
    }
}

@Composable
fun QueueStatTile(label: String, value: String, subtext: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = TextSecondary, fontSize = 12.sp)
        Text(value, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = color)
        Text(subtext, color = TextSecondary, fontSize = 10.sp)
    }
}

@Composable
fun MetricRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = TextSecondary, fontSize = 13.sp)
        Text(value, color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
    }
}
