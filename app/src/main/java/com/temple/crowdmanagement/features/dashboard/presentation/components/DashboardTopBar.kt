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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardTopBar(
    isTempleOpen: Boolean = true,
    crowdFlowLabel: String = "Smooth Flow"
) {
    // Warm maroon gradient matching the reference image header
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(SaffronDark, SaffronPrimary)
                )
            )
            .padding(top = 48.dp, bottom = 12.dp, start = 20.dp, end = 20.dp)
    ) {
        Column {
            // Temple identity row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🛕", fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Somnath Temple",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = 0.2.sp
                        )
                        Text(
                            text = "Veraval, Gujarat",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
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

            // Darshan status pill
            Row(verticalAlignment = Alignment.CenterVertically) {
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
            }
        }
    }
}