package com.temple.crowdmanagement.features.guide.components

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
import androidx.compose.foundation.clickable
import com.temple.crowdmanagement.ui.theme.*

@Composable
fun TempleInfoCard(
    templeName: String,
    location: String,
    description: String,
    history: String,
    architecture: String,
    timings: String
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp), clip = false),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardDarkBg
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Temple Name & Location
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Place,
                    contentDescription = "Temple",
                    tint = SaffronPrimary,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = templeName,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = SaffronPrimary
                    )
                    Text(
                        text = "📍 $location",
                        fontSize = 14.sp,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            Text(
                text = description,
                fontSize = 14.sp,
                color = TextPrimary,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Read More / Show Less
            if (!expanded) {
                Text(
                    text = "Read More...",
                    fontSize = 14.sp,
                    color = SaffronPrimary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { expanded = true }
                )
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(12.dp))
                
                // History
                Text(
                    text = "📜 History",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = SaffronPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = history,
                    fontSize = 14.sp,
                    color = TextPrimary,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Architecture
                Text(
                    text = "🏛️ Architecture",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = SaffronPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = architecture,
                    fontSize = 14.sp,
                    color = TextPrimary,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Show Less",
                    fontSize = 14.sp,
                    color = SaffronPrimary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { expanded = false }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Timings
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = SurfaceVariantDark,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.AccessTime,
                            contentDescription = "Timings",
                            tint = SaffronPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Temple Timings",
                            fontSize = 14.sp,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        text = timings,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = SaffronPrimary
                    )
                }
            }
        }
    }
}