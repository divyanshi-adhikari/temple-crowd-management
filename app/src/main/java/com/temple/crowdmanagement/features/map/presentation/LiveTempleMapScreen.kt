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
    var selectedTemple by remember { mutableStateOf(TempleSite.SOMNATH) }
    val heatZones = remember(selectedTemple) { TempleZoneData.getZonesForTemple(selectedTemple) }
    val pois      = remember(selectedTemple) { TempleZoneData.getPOIsForTemple(selectedTemple) }

    var visiblePoiTypes by remember { mutableStateOf(POIType.values().toSet()) }
    var selectedPoi     by remember { mutableStateOf<ZonePOI?>(null) }
    var selectedZone    by remember { mutableStateOf<HeatZone?>(null) }

    var zoomScale by remember { mutableFloatStateOf(1f) }
    var panOffset by remember { mutableStateOf(androidx.compose.ui.geometry.Offset.Zero) }

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
                .padding(top = 30.dp, bottom = 12.dp, start = 20.dp, end = 20.dp)
        ) {
            Column {
                Text(
                    text = "Live Masterplan & Crowd Heatmap",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "${selectedTemple.displayName} · Real-time AI heat zones & POIs",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Temple Switcher Tabs (Somnath vs Dwarka)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(TempleSite.SOMNATH, TempleSite.DWARKA).forEach { site ->
                        val isCurrent = selectedTemple == site
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isCurrent) GoldAccent else Color.White.copy(alpha = 0.18f),
                            modifier = Modifier.clickable {
                                selectedTemple = site
                                selectedPoi = null
                                selectedZone = null
                                zoomScale = 1f
                                panOffset = androidx.compose.ui.geometry.Offset.Zero
                            }
                        ) {
                            Text(
                                text = if (site == TempleSite.SOMNATH) "Somnath Masterplan" else "Dwarka Masterplan",
                                color = if (isCurrent) Color(0xFF1E1405) else Color.White,
                                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp)
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

            Spacer(modifier = Modifier.height(6.dp))

            // Density Legend
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CardDarkBg, RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                DensityIndicator("Low",      StatusGreen)
                DensityIndicator("Moderate", StatusOrange)
                DensityIndicator("Heavy",    StatusRed)
                DensityIndicator("Critical", Color(0xFFB71C1C))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Map Canvas Box with Floating Zoom Controls
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F0D11)),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(1.dp, SurfaceVariantDark, RoundedCornerShape(16.dp))
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    InteractiveTempleCanvas(
                        temple          = selectedTemple,
                        heatZones       = heatZones,
                        pois            = pois,
                        visiblePoiTypes = visiblePoiTypes,
                        zoomScale       = zoomScale,
                        panOffset       = panOffset,
                        onTransform     = { zChange, pChange ->
                            val nextScale = (zoomScale * zChange).coerceIn(1f, 4.5f)
                            zoomScale = nextScale
                            panOffset = if (nextScale <= 1.05f) {
                                androidx.compose.ui.geometry.Offset.Zero
                            } else {
                                androidx.compose.ui.geometry.Offset(
                                    panOffset.x + pChange.x,
                                    panOffset.y + pChange.y
                                )
                            }
                        },
                        onPoiClicked    = { selectedPoi = it },
                        onZoneClicked   = { selectedZone = it }
                    )

                    // Zoom / Pan Overlay Controls
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // Zoom In (+)
                        SmallFloatingActionButton(
                            onClick = {
                                zoomScale = (zoomScale * 1.25f).coerceAtMost(4.5f)
                            },
                            containerColor = CardDarkBg.copy(alpha = 0.9f),
                            contentColor = GoldAccent,
                            shape = CircleShape
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Zoom In", modifier = Modifier.size(18.dp))
                        }

                        // Zoom Out (-)
                        SmallFloatingActionButton(
                            onClick = {
                                val nextScale = (zoomScale / 1.25f).coerceAtLeast(1f)
                                zoomScale = nextScale
                                if (nextScale == 1f) panOffset = androidx.compose.ui.geometry.Offset.Zero
                            },
                            containerColor = CardDarkBg.copy(alpha = 0.9f),
                            contentColor = GoldAccent,
                            shape = CircleShape
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "Zoom Out", modifier = Modifier.size(18.dp))
                        }

                        // Reset View
                        SmallFloatingActionButton(
                            onClick = {
                                zoomScale = 1f
                                panOffset = androidx.compose.ui.geometry.Offset.Zero
                            },
                            containerColor = CardDarkBg.copy(alpha = 0.9f),
                            contentColor = TextPrimary,
                            shape = CircleShape
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = "Reset View", modifier = Modifier.size(18.dp))
                        }
                    }

                    // Map Hint Badge (Pinch & drag indicator)
                    Surface(
                        color = Color.Black.copy(alpha = 0.65f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Pinch or tap +/- to zoom · Tap pins for details",
                            color = Color.White.copy(alpha = 0.75f),
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
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
