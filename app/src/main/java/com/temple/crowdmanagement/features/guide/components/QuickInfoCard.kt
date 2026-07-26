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
import com.temple.crowdmanagement.features.guide.model.QuickFact
import com.temple.crowdmanagement.ui.theme.*

@Composable
fun QuickInfoCard(
    quickFacts: List<QuickFact>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(20.dp), clip = false),
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
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SaffronPrimary.copy(alpha = 0.15f),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = "Quick Info",
                            tint = SaffronPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = "Quick Facts",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Essential temple information",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 2x2 Grid (2 Columns, 4 Rows)
            val rows = quickFacts.chunked(2)

            rows.forEachIndexed { rowIndex, rowFacts ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowFacts.forEach { fact ->
                        FactCard(
                            modifier = Modifier.weight(1f),
                            fact = fact
                        )
                    }
                    // Fill empty space if odd number
                    if (rowFacts.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                // Add spacing between rows (except after last row)
                if (rowIndex < rows.size - 1) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun FactCard(
    modifier: Modifier = Modifier,
    fact: QuickFact
) {
    Card(
        modifier = modifier
            .shadow(4.dp, RoundedCornerShape(14.dp), clip = false),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.06f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Circle
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = SaffronPrimary.copy(alpha = 0.15f),
                modifier = Modifier.size(40.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = fact.icon,
                        fontSize = 20.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Text
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Value (bold text) - e.g., "Lord Krishna"
                Text(
                    text = fact.value,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1
                )
                // Label (small text) - e.g., "Deity"
                Text(
                    text = fact.label,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    maxLines = 1
                )
            }
        }
    }
}