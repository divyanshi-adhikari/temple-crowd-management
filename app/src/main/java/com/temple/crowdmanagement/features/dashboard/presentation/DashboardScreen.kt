package com.temple.crowdmanagement.features.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.temple.crowdmanagement.ui.theme.*

@Composable
fun DashboardScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpiritualDarkBg)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Pilgrim Home Dashboard",
                    style = MaterialTheme.typography.headlineLarge,
                    color = GoldAccent
                )
                Text(
                    text = "Gujarat Temple Network Overview",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
            Surface(
                color = SandstoneGold.copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "👉 DEV 1 WORKSPACE",
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
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardDarkBg),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CloudQueue, contentDescription = null, tint = GoldAccent)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Live Crowd & AI Surge Forecast", fontWeight = FontWeight.Bold, color = TextPrimary)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Developer 1: Build live crowd status widgets, weather updates, temple timings, and AI best-time-to-visit recommendations here.",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardDarkBg),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Featured Pilgrimage Sites", fontWeight = FontWeight.Bold, color = GoldAccent)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("• Somnath Temple (Veraval)", color = TextPrimary, fontSize = 13.sp)
                        Text("• Dwarkadhish Temple (Dwarka)", color = TextPrimary, fontSize = 13.sp)
                        Text("• Ambaji Temple (Banaskantha)", color = TextPrimary, fontSize = 13.sp)
                        Text("• Kalika Mata Temple (Pavagadh)", color = TextPrimary, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}
