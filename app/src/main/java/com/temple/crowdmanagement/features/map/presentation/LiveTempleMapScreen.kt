package com.temple.crowdmanagement.features.map.presentation

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.temple.crowdmanagement.core.model.*
import com.temple.crowdmanagement.features.map.data.TempleZoneData
import com.temple.crowdmanagement.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveTempleMapScreen() {
    val heatZones = remember { TempleZoneData.getZonesForTemple(TempleSite.SOMNATH) }
    val pois = remember { TempleZoneData.getPOIsForTemple(TempleSite.SOMNATH) }

    var visiblePoiTypes by remember {
        mutableStateOf(POIType.values().toSet())
    }

    var selectedPoi by remember { mutableStateOf<ZonePOI?>(null) }
    var selectedZone by remember { mutableStateOf<HeatZone?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpiritualDarkBg)
            .padding(16.dp)
    ) {
        // Header
        Column {
            Text(
                text = "⚑  SOMNATH TEMPLE · LIVE HEATMAP",
                color = SaffronPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.8.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Live Temple Heatmap",
                style = MaterialTheme.typography.headlineLarge,
                color = TextPrimary
            )
            Text(
                text = "Real-time AI crowd density & POI radar",
                color = TextSecondary,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Filter Layer Chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(POIType.values()) { poiType ->
                val isSelected = visiblePoiTypes.contains(poiType)
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        visiblePoiTypes = if (isSelected) {
                            visiblePoiTypes - poiType
                        } else {
                            visiblePoiTypes + poiType
                        }
                    },
                    label = { Text(poiType.name.replace("_", " "), fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = SaffronPrimary,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Live Density Legend
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardDarkBg, RoundedCornerShape(8.dp))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            DensityIndicator("Low", StatusGreen)
            DensityIndicator("Moderate", StatusOrange)
            DensityIndicator("Heavy", StatusRed)
            DensityIndicator("Critical", Color(0xFFB71C1C))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Map Canvas Box
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF121416)),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(1.dp, SurfaceVariantDark, RoundedCornerShape(16.dp))
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                InteractiveTempleCanvas(
                    heatZones = heatZones,
                    pois = pois,
                    visiblePoiTypes = visiblePoiTypes,
                    onPoiClicked = { selectedPoi = it },
                    onZoneClicked = { selectedZone = it }
                )
            }
        }

        // Selected POI or Zone Info Drawer Card
        selectedPoi?.let { poi ->
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = CardDarkBg),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = when (poi.type) {
                            POIType.ENTRY_GATE -> Icons.Default.Login
                            POIType.EXIT_GATE -> Icons.Default.Logout
                            POIType.PARKING -> Icons.Default.LocalParking
                            POIType.WASHROOM -> Icons.Default.Wc
                            POIType.MEDICAL -> Icons.Default.MedicalServices
                            POIType.WATER -> Icons.Default.WaterDrop
                            POIType.SECURITY -> Icons.Default.Shield
                        },
                        contentDescription = null,
                        tint = GoldAccent,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(poi.name, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text(poi.details, color = TextSecondary, fontSize = 12.sp)
                    }
                    IconButton(onClick = { selectedPoi = null }) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }
            }
        }

        selectedZone?.let { zone ->
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = CardDarkBg),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(Color(zone.density.colorHex), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(zone.zoneName, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text("Current Crowd: ${zone.currentCount} / ${zone.maxCapacity} devotees", color = TextSecondary, fontSize = 12.sp)
                        Text("Status: ${zone.density.label}", color = Color(zone.density.colorHex), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                    IconButton(onClick = { selectedZone = null }) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }
            }
        }
    }
}

@Composable
fun DensityIndicator(label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(label, fontSize = 11.sp, color = TextSecondary)
    }
}
