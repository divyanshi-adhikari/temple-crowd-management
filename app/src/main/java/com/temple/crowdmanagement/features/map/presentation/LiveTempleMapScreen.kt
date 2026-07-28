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
import androidx.compose.ui.graphics.Brush
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
    val pois      = remember { TempleZoneData.getPOIsForTemple(TempleSite.SOMNATH) }

    var visiblePoiTypes by remember { mutableStateOf(POIType.values().toSet()) }
    var selectedPoi     by remember { mutableStateOf<ZonePOI?>(null) }
    var selectedZone    by remember { mutableStateOf<HeatZone?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpiritualDarkBg)
    ) {
        // ── Screen Header (maroon top bar style) ─────────────────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(listOf(SaffronDark, SaffronPrimary)))
                .padding(top = 48.dp, bottom = 14.dp, start = 20.dp, end = 20.dp)
        ) {
            Column {
                Text(
                    text = "Live Temple Heatmap",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Somnath · Real-time AI crowd density",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.75f)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // POI Filter Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(POIType.values()) { poiType ->
                    val isSelected = visiblePoiTypes.contains(poiType)
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            visiblePoiTypes = if (isSelected) visiblePoiTypes - poiType
                                             else visiblePoiTypes + poiType
                        },
                        label = { Text(poiType.name.replace("_", " "), fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SaffronPrimary,
                            selectedLabelColor     = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Density Legend
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CardDarkBg, RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                DensityIndicator("Low",      StatusGreen)
                DensityIndicator("Moderate", StatusOrange)
                DensityIndicator("Heavy",    StatusRed)
                DensityIndicator("Critical", Color(0xFFB71C1C))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Map Canvas
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F0D11)),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(1.dp, SurfaceVariantDark, RoundedCornerShape(16.dp))
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    InteractiveTempleCanvas(
                        heatZones       = heatZones,
                        pois            = pois,
                        visiblePoiTypes = visiblePoiTypes,
                        onPoiClicked    = { selectedPoi = it },
                        onZoneClicked   = { selectedZone = it }
                    )
                }
            }

            // POI Detail Drawer
            selectedPoi?.let { poi ->
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardDarkBg),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = when (poi.type) {
                                POIType.ENTRY_GATE -> Icons.Default.Login
                                POIType.EXIT_GATE  -> Icons.Default.Logout
                                POIType.PARKING    -> Icons.Default.LocalParking
                                POIType.WASHROOM   -> Icons.Default.Wc
                                POIType.MEDICAL    -> Icons.Default.MedicalServices
                                POIType.WATER      -> Icons.Default.WaterDrop
                                POIType.SECURITY   -> Icons.Default.Shield
                            },
                            contentDescription = null,
                            tint = GoldAccent,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(poi.name, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                            Text(poi.details, color = TextSecondary, fontSize = 12.sp)
                        }
                        IconButton(onClick = { selectedPoi = null }) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                        }
                    }
                }
            }

            // Zone Detail Drawer
            selectedZone?.let { zone ->
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardDarkBg),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .background(Color(zone.density.colorHex), CircleShape)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(zone.zoneName, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                            Text(
                                "${zone.currentCount} / ${zone.maxCapacity} devotees · ${zone.density.label}",
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                        }
                        IconButton(onClick = { selectedZone = null }) {
                            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                        }
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
                .size(8.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(label, fontSize = 11.sp, color = TextSecondary)
    }
}
