package com.temple.crowdmanagement.features.security.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.temple.crowdmanagement.features.security.model.DutyStatus
import com.temple.crowdmanagement.ui.theme.*

@Composable
fun DutyStatusDialog(
    currentStatus: DutyStatus,
    onDismiss: () -> Unit,
    onStatusSelected: (DutyStatus) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = SpiritualDarkBg
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Change Duty Status",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Select your current duty status",
                    fontSize = 14.sp,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ✅ Fixed: Use DutyStatus directly instead of DutyStatusOption
                DutyStatus.values().forEach { status ->
                    StatusOptionItem(
                        status = status,
                        isSelected = status == currentStatus,
                        onClick = {
                            onStatusSelected(status)
                            onDismiss()
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Cancel",
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun StatusOptionItem(
    status: DutyStatus,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val icon = when (status) {
        DutyStatus.ON_DUTY -> "🟢"
        DutyStatus.ON_BREAK -> "🟡"
        DutyStatus.OFF_DUTY -> "🔴"
    }
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) 
            SaffronPrimary.copy(alpha = 0.15f) 
        else 
            Color.White.copy(alpha = 0.05f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = icon,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = status.displayName,
                fontSize = 16.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) SaffronPrimary else Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            if (isSelected) {
                Text(
                    text = "✓",
                    color = SaffronPrimary,
                    fontSize = 18.sp
                )
            }
        }
    }
}