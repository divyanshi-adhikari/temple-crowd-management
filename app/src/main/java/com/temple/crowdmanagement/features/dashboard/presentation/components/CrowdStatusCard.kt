package com.temple.crowdmanagement.features.dashboard.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
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
    val pct = crowdPercentage.coerceIn(0, 100)
    val statusColor = when (status.lowercase()) {
        "low"      -> StatusGreen
        "moderate" -> StatusOrange
        "high"     -> StatusRed
        else       -> StatusGreen
    }
    val flowLabel = when (status.lowercase()) {
        "low"      -> "Smooth Flow"
        "moderate" -> "Moderate Flow"
        "high"     -> "Heavy Flow"
        else       -> "Smooth Flow"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardDarkBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            // ── Ring Gauge + Status Label ─────────────────────────
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Circular Ring Meter
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(120.dp)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val strokeWidth = 14.dp.toPx()
                        val sweepTotal  = 240f
                        val startAngle  = 150f
                        val inset       = strokeWidth / 2f
                        val arcSize     = Size(size.width - strokeWidth, size.height - strokeWidth)
                        val topLeft     = Offset(inset, inset)

                        // Track
                        drawArc(
                            color      = SurfaceVariantDark,
                            startAngle = startAngle,
                            sweepAngle = sweepTotal,
                            useCenter  = false,
                            topLeft    = topLeft,
                            size       = arcSize,
                            style      = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                        // Progress
                        drawArc(
                            color      = statusColor,
                            startAngle = startAngle,
                            sweepAngle = sweepTotal * (pct / 100f),
                            useCenter  = false,
                            topLeft    = topLeft,
                            size       = arcSize,
                            style      = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                    }
                    // Percentage label inside ring
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$pct%",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "full",
                            fontSize = 10.sp,
                            color = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.width(20.dp))

                // Status text block
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Live Crowd",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        letterSpacing = 0.3.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = status.uppercase(),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = statusColor
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(statusColor, shape = RoundedCornerShape(50))
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = flowLabel,
                            fontSize = 12.sp,
                            color = statusColor
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Updated $lastUpdated",
                        fontSize = 11.sp,
                        color = TextTertiary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── Divider ──────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(SurfaceVariantDark)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ── Stat Tiles Row ───────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                StatTile(label = "Est. Wait",    value = waitTime,        unit = "mins")
                StatTile(label = "Best Time",    value = bestTime,        unit = "")
                StatTile(label = "Today",        value = totalVisitors,   unit = "visitors")
            }
        }
    }
}

@Composable
private fun StatTile(label: String, value: String, unit: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            fontSize = 10.sp,
            color = TextSecondary,
            letterSpacing = 0.3.sp
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = value,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        if (unit.isNotBlank()) {
            Text(
                text = unit,
                fontSize = 9.sp,
                color = TextTertiary
            )
        }
    }
}

// Keep StatItem for CrowdStatusCard compatibility with other references
@Composable
fun StatItem(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    icon: ImageVector,
    color: Color
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Text(text = label, fontSize = 10.sp, color = TextSecondary)
    }
}