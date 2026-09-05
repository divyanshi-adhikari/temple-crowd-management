package com.temple.crowdmanagement.features.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.temple.crowdmanagement.ui.theme.*

import androidx.compose.runtime.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.filled.KeyboardArrowDown
import com.temple.crowdmanagement.core.model.TempleSite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardTopBar(
    selectedTemple: TempleSite = TempleSite.SOMNATH,
    onSelectTemple: (TempleSite) -> Unit = {},
    isTempleOpen: Boolean = true,
    crowdFlowLabel: String = "Smooth Flow"
) {
    var isDropdownExpanded by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }

    // Warm maroon gradient matching the reference image header
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(SaffronDark, SaffronPrimary)
                )
            )
            .padding(top = 30.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
    ) {
        Column {
            // Temple identity row with selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { isDropdownExpanded = true }
                        .padding(vertical = 4.dp)
                ) {
                    Text(text = "🛕", fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = selectedTemple.displayName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 0.2.sp
                            )
                            Icon(
                                Icons.Default.KeyboardArrowDown,
                                contentDescription = "Select Temple",
                                tint = Color.White.copy(alpha = 0.8f),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = selectedTemple.location,
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }

                    DropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false },
                        modifier = Modifier.background(CardDarkBg)
                    ) {
                        TempleSite.values().forEach { site ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text(site.displayName, fontWeight = FontWeight.Bold, color = if (site == selectedTemple) GoldAccent else TextPrimary)
                                        Text(site.location, fontSize = 11.sp, color = TextSecondary)
                                    }
                                },
                                onClick = {
                                    onSelectTemple(site)
                                    isDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                // Notification bell
                IconButton(onClick = {}) {
                    BadgedBox(
                        badge = {
                            Badge(
                                containerColor = GoldAccent,
                                contentColor = Color.Black
                            ) { Text("3", fontSize = 9.sp, fontWeight = FontWeight.Bold) }
                        }
                    ) {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Darshan status pill & quick temple chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White.copy(alpha = 0.15f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .background(StatusGreen, RoundedCornerShape(50))
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isTempleOpen) "Darshan Open  •  $crowdFlowLabel"
                                   else "Darshan Closed",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }

                TempleSite.values().forEach { site ->
                    val isSelected = site == selectedTemple
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) GoldAccent.copy(alpha = 0.35f) else Color.White.copy(alpha = 0.1f),
                        modifier = Modifier.clickable { onSelectTemple(site) }
                    ) {
                        Text(
                            text = site.name.lowercase().capitalize(),
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}