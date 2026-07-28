package com.temple.crowdmanagement.features.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.temple.crowdmanagement.ui.theme.*

@Composable
fun ActionsGrid(
    onLiveMapClick: () -> Unit,
    onBookDarshanClick: () -> Unit,
    onSOSClick: () -> Unit,
    onTempleGuideClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Primary actions — large full-width cards
        ActionRow(
            icon       = Icons.Default.ConfirmationNumber,
            iconBg     = SaffronPrimary,
            label      = "Book Darshan Pass",
            sublabel   = "Reserve your Darshan slot",
            onClick    = onBookDarshanClick
        )
        ActionRow(
            icon       = Icons.Default.Map,
            iconBg     = Color(0xFF1A3A5C),
            label      = "View Live Heatmap",
            sublabel   = "Real-time crowd density map",
            onClick    = onLiveMapClick
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Secondary actions — two-column smaller cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SecondaryActionCard(
                modifier   = Modifier.weight(1f),
                icon       = Icons.Default.MenuBook,
                iconBg     = Color(0xFF2A3A28),
                iconTint   = StatusGreen,
                label      = "Temple Guide",
                onClick    = onTempleGuideClick
            )
            SecondaryActionCard(
                modifier   = Modifier.weight(1f),
                icon       = Icons.Default.Queue,
                iconBg     = Color(0xFF3A2A1A),
                iconTint   = GoldAccent,
                label      = "Virtual Queue",
                onClick    = onSOSClick  // navigates to queue tab
            )
        }
    }
}

// Full-width action card (Book Darshan / Live Map style from reference)
@Composable
private fun ActionRow(
    icon: ImageVector,
    iconBg: Color,
    label: String,
    sublabel: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardDarkBg),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon container
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Text(
                    text = sublabel,
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = TextTertiary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// Smaller square action card for secondary actions
@Composable
private fun SecondaryActionCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    label: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardDarkBg),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = label, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
        }
    }
}