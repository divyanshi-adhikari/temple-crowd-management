package com.temple.crowdmanagement.features.dashboard.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.temple.crowdmanagement.ui.theme.*

@Composable
fun TodayEventsCard(
    events: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardDarkBg
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "📅", fontSize = 24.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Today's Events",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            events.forEachIndexed { index, event ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = StatusGreen.copy(alpha = 0.15f),
                        modifier = Modifier.size(24.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "✔",
                                color = StatusGreen,
                                fontSize = 14.sp
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Text(
                        text = event,
                        fontSize = 14.sp,
                        color = TextPrimary,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                if (index != events.size - 1) {
                    Divider(
                        color = SurfaceVariantDark,
                        modifier = Modifier.padding(start = 36.dp, top = 2.dp, bottom = 2.dp)
                    )
                }
            }
        }
    }
}