package com.temple.crowdmanagement.features.security.components

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
import com.temple.crowdmanagement.ui.theme.*

@Composable
fun QuickActionsSection(
    onReportIncident: () -> Unit,
    onContactController: () -> Unit,
    onEmergency: () -> Unit,
    onNotifications: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(20.dp), clip = false),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = SpiritualDarkBg  // ✅ Using the correct background color
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "Quick Actions",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionItem(
                    modifier = Modifier.weight(1f),
                    icon = "🚨",
                    label = "Report Incident",
                    color = StatusRed,
                    onClick = onReportIncident
                )
                QuickActionItem(
                    modifier = Modifier.weight(1f),
                    icon = "📢",
                    label = "Contact Controller",
                    color = SaffronPrimary,
                    onClick = onContactController
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                QuickActionItem(
                    modifier = Modifier.weight(1f),
                    icon = "🆘",
                    label = "Emergency",
                    color = StatusRed,
                    onClick = onEmergency
                )
                QuickActionItem(
                    modifier = Modifier.weight(1f),
                    icon = "🔔",
                    label = "Notifications",
                    color = GoldAccent,
                    onClick = onNotifications
                )
            }
        }
    }
}

@Composable
fun QuickActionItem(
    modifier: Modifier = Modifier,
    icon: String,
    label: String,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(72.dp)
            .clickable { onClick() }
            .shadow(2.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = icon,
                fontSize = 24.sp
            )
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = color,
                maxLines = 1
            )
        }
    }
}