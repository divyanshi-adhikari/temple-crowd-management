package com.temple.crowdmanagement.features.dashboard.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import com.temple.crowdmanagement.ui.theme.*

/**
 * Simple greeting header shown below the top bar on the dashboard.
 * Intentionally minimal — let the CrowdStatusCard be the visual hero.
 */
@Composable
fun HeaderSection(
    name: String,
    isOpen: Boolean
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = if (name.isNotBlank()) "Welcome, $name" else "Welcome",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Today's live conditions at Somnath",
            fontSize = 13.sp,
            color = TextSecondary
        )
    }
}