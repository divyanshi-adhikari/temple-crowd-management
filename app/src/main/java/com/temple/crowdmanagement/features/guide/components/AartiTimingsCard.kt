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
import com.temple.crowdmanagement.features.guide.model.AartiTiming
import com.temple.crowdmanagement.ui.theme.*

@Composable
fun AartiTimingsCard(
    aartiTimings: List<AartiTiming>
) {
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
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Grade,
                    contentDescription = "Aarti",
                    tint = SaffronPrimary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Aarti Timings",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.weight(1f))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SaffronPrimary.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "${aartiTimings.size} Daily",
                        fontSize = 12.sp,
                        color = SaffronPrimary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Aarti List
            aartiTimings.forEachIndexed { index, aarti ->
                AartiItem(
                    name = aarti.name,
                    time = aarti.time,
                    description = aarti.description,
                    isSpecial = aarti.isSpecial
                )
                
                if (index != aartiTimings.size - 1) {
                    Divider(
                        color = SurfaceVariantDark,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            // Special Note
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = SandstoneGold.copy(alpha = 0.15f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "✨",
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Special Aarti on festivals and special occasions",
                        fontSize = 13.sp,
                        color = GoldAccent,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun AartiItem(
    name: String,
    time: String,
    description: String,
    isSpecial: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Time
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = SaffronPrimary.copy(alpha = 0.15f)
        ) {
            Text(
                text = time,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = SaffronPrimary,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Name
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }
        }

        // Special Badge
        if (isSpecial) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = GoldAccent.copy(alpha = 0.15f)
            ) {
                Text(
                    text = "🌟 Special",
                    fontSize = 10.sp,
                    color = GoldAccent,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}