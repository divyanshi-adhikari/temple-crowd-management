package com.temple.crowdmanagement.features.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable  
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.temple.crowdmanagement.ui.theme.*

@Composable
fun CrowdStatusCard(
    status: String,
    waitTime: String,
    totalVisitors: String,
    lastUpdated: String,
    crowdPercentage: Int,
    bestTime: String,
    confidence: Int
) {
    //  Safe status handling
    val safeStatus = status.lowercase()
    val statusColor = when (safeStatus) {
        "low" -> Color.Green
        "moderate" -> SaffronLight
        "high" -> Color.Red
        else -> Color.Green
    }
    
    //  Status emoji matches actual status
    val statusEmoji = when (safeStatus) {
        "low" -> "🟢"
        "moderate" -> "🟡"
        "high" -> "🔴"
        else -> "🟢"
    }
    
    //  Hero card icon matches status
    val heroEmoji = when (safeStatus) {
        "low" -> "🟢"
        "moderate" -> "🟡"
        "high" -> "🔴"
        else -> "🟢"
    }
    
    //  Safe percentage (0-100)
    val safePercentage = crowdPercentage.coerceIn(0, 100)
    val safeConfidence = confidence.coerceIn(0, 100)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(24.dp),
                clip = false
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = SpiritualDarkBg
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp)
        ) {
            // Header with dynamic icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                   
                    Text(
                        text = heroEmoji,
                        fontSize = 22.sp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "LIVE CROWD",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                }
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = statusColor.copy(alpha = 0.15f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = statusEmoji,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = status.uppercase(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = statusColor,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Big Status Display
            Text(
                text = status.uppercase(),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = statusColor,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "Crowd Level",
                fontSize = 14.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Stats Row - Improved with maxLines
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatItem(
                    modifier = Modifier.weight(1f),
                    label = "Wait Time",
                    value = waitTime,
                    icon = Icons.Default.AccessTime,
                    color = SaffronLight
                )
                StatItem(
                    modifier = Modifier.weight(1f),
                    label = "Visitors",
                    value = totalVisitors,
                    icon = Icons.Default.People,
                    color = SaffronPrimary
                )
                StatItem(
                    modifier = Modifier.weight(1f),
                    label = "Best Time",
                    value = bestTime,
                    icon = Icons.Default.Star,
                    color = SandstoneGold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Crowd Meter
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Crowd Meter",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextSecondary
                    )
                    Text(
                        text = "$safePercentage%",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusColor
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .shadow(2.dp, RoundedCornerShape(6.dp))
                        .background(
                            Color.Gray.copy(alpha = 0.15f),
                            RoundedCornerShape(6.dp)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            
                            .fillMaxWidth(safePercentage / 100f)
                            .height(10.dp)
                            .background(
                                Brush.horizontalGradient(
                                    colors = when {
                                        safePercentage > 70 -> listOf(Color.Red, SaffronLight)
                                        safePercentage > 40 -> listOf(SaffronLight, SaffronPrimary)
                                        else -> listOf(Color.Green, SaffronLight)
                                    }
                                ),
                                RoundedCornerShape(6.dp)
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // AI Confidence Badge
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🤖", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "AI Accuracy",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextSecondary
                    )
                }
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SandstoneGold.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "$safeConfidence%",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = SandstoneGold,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp)
                    )
                }
            }
            
            // AI Confidence Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(Color.Gray.copy(alpha = 0.15f), RoundedCornerShape(2.dp))
            ) {
                Box(
                    modifier = Modifier
                        
                        .fillMaxWidth(safeConfidence / 100f)
                        .height(4.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(SandstoneGold, SaffronPrimary)
                            ),
                            RoundedCornerShape(2.dp)
                        )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "⏱ Updated $lastUpdated",
                    fontSize = 11.sp,
                    color = TextSecondary.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun StatItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    icon: ImageVector,
    color: Color
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 1  
            )
        }
        Text(
            text = label,
            fontSize = 10.sp,
            color = TextSecondary,
            maxLines = 1  
        )
    }
}