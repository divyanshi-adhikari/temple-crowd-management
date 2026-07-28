package com.temple.crowdmanagement.features.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow  
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.temple.crowdmanagement.ui.theme.*


@Composable
fun CrowdPredictionCard(
    prediction: String,
    confidence: Int,
    bestTime: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(20.dp),
                clip = false
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardDarkBg
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "🤖", fontSize = 28.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "AI Prediction",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Next Hour Forecast",
                fontSize = 12.sp,
                color = TextSecondary
            )
            
            Text(
                text = prediction,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = SaffronPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Confidence", fontSize = 11.sp, color = TextSecondary)
                    Text(
                        text = "$confidence%",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = SandstoneGold
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Best Time", fontSize = 11.sp, color = TextSecondary)
                    Text(
                        text = bestTime,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = StatusGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(Color.Gray.copy(alpha = 0.15f), RoundedCornerShape(2.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(confidence / 100f)
                        .height(4.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(SandstoneGold, SaffronPrimary)
                            ),
                            RoundedCornerShape(2.dp)
                        )
                )
            }
        }
    }
}