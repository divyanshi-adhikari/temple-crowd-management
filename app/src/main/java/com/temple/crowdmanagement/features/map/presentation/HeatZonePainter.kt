package com.temple.crowdmanagement.features.map.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import com.temple.crowdmanagement.core.model.HeatZone
import com.temple.crowdmanagement.core.model.POIType
import com.temple.crowdmanagement.core.model.ZonePOI
import com.temple.crowdmanagement.ui.theme.*

@Composable
fun InteractiveTempleCanvas(
    heatZones: List<HeatZone>,
    pois: List<ZonePOI>,
    visiblePoiTypes: Set<POIType>,
    onPoiClicked: (ZonePOI) -> Unit,
    onZoneClicked: (HeatZone) -> Unit
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(heatZones, pois, visiblePoiTypes) {
                detectTapGestures { tapOffset ->
                    val width = size.width
                    val height = size.height

                    // Check POI clicks
                    val clickedPoi = pois.firstOrNull { poi ->
                        if (visiblePoiTypes.contains(poi.type)) {
                            val poiOffset = Offset(poi.xPercent * width, poi.yPercent * height)
                            (tapOffset - poiOffset).getDistance() < 40f
                        } else false
                    }
                    if (clickedPoi != null) {
                        onPoiClicked(clickedPoi)
                        return@detectTapGestures
                    }

                    // Check HeatZone clicks
                    val clickedZone = heatZones.firstOrNull { zone ->
                        val zoneOffset = Offset(zone.relativeX * width, zone.relativeY * height)
                        val radius = zone.radiusRatio * width
                        (tapOffset - zoneOffset).getDistance() < radius
                    }
                    if (clickedZone != null) {
                        onZoneClicked(clickedZone)
                    }
                }
            }
    ) {
        val w = size.width
        val h = size.height

        // 1. Draw Outer Temple Wall Blueprint Canvas
        val wallPath = Path().apply {
            moveTo(w * 0.1f, h * 0.1f)
            lineTo(w * 0.9f, h * 0.1f)
            lineTo(w * 0.9f, h * 0.85f)
            lineTo(w * 0.1f, h * 0.85f)
            close()
        }
        drawPath(
            path = wallPath,
            color = SandstoneGold.copy(alpha = 0.3f),
            style = Stroke(width = 6f)
        )

        // Draw Inner Temple Sanctum Structure
        val innerSanctum = Path().apply {
            moveTo(w * 0.35f, h * 0.25f)
            lineTo(w * 0.65f, h * 0.25f)
            lineTo(w * 0.65f, h * 0.55f)
            lineTo(w * 0.35f, h * 0.55f)
            close()
        }
        drawPath(
            path = innerSanctum,
            color = SaffronPrimary.copy(alpha = 0.4f),
            style = Stroke(width = 4f)
        )

        // 2. Draw Crowd Heat Zones with Radial Gradients
        heatZones.forEach { zone ->
            val center = Offset(zone.relativeX * w, zone.relativeY * h)
            val radius = zone.radiusRatio * w
            val baseColor = Color(zone.density.colorHex)

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        baseColor.copy(alpha = 0.65f),
                        baseColor.copy(alpha = 0.3f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = radius
                ),
                center = center,
                radius = radius
            )

            // Inner core indicator
            drawCircle(
                color = baseColor,
                center = center,
                radius = 12f
            )
        }

        // 3. Draw POI Icon Badges
        pois.forEach { poi ->
            if (visiblePoiTypes.contains(poi.type)) {
                val center = Offset(poi.xPercent * w, poi.yPercent * h)
                val badgeColor = when (poi.type) {
                    POIType.ENTRY_GATE -> StatusGreen
                    POIType.EXIT_GATE -> StatusOrange
                    POIType.PARKING -> SandstoneGold
                    POIType.WASHROOM -> Color(0xFF0288D1)
                    POIType.MEDICAL -> StatusRed
                    POIType.WATER -> Color(0xFF00ACC1)
                    POIType.SECURITY -> Color(0xFF7B1FA2)
                }

                // Outer halo badge
                drawCircle(
                    color = badgeColor,
                    center = center,
                    radius = 20f
                )
                drawCircle(
                    color = Color.White,
                    center = center,
                    radius = 14f
                )
                drawCircle(
                    color = badgeColor,
                    center = center,
                    radius = 8f
                )
            }
        }
    }
}
