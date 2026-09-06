package com.temple.crowdmanagement.features.map.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.temple.crowdmanagement.R
import com.temple.crowdmanagement.core.model.HeatZone
import com.temple.crowdmanagement.core.model.POIType
import com.temple.crowdmanagement.core.model.TempleSite
import com.temple.crowdmanagement.core.model.ZonePOI
import com.temple.crowdmanagement.ui.theme.*

@Composable
fun InteractiveTempleCanvas(
    temple: TempleSite,
    heatZones: List<HeatZone>,
    pois: List<ZonePOI>,
    visiblePoiTypes: Set<POIType>,
    zoomScale: Float = 1f,
    panOffset: Offset = Offset.Zero,
    onTransform: (scaleChange: Float, panChange: Offset) -> Unit = { _, _ -> },
    onPoiClicked: (ZonePOI) -> Unit,
    onZoneClicked: (HeatZone) -> Unit
) {
    val mapDrawableRes = when (temple) {
        TempleSite.DWARKA -> R.drawable.map_dwarka_masterplan
        else -> R.drawable.map_somnath_masterplan
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A090C))
            .pointerInput(temple) {
                detectTransformGestures { _, pan, zoom, _ ->
                    onTransform(zoom, pan)
                }
            }
    ) {
        // 1. Base Masterplan Satellite / Architectural Map Graphic
        Image(
            painter = painterResource(id = mapDrawableRes),
            contentDescription = "${temple.displayName} Masterplan Map",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = zoomScale
                    scaleY = zoomScale
                    translationX = panOffset.x
                    translationY = panOffset.y
                }
        )

        // 2. Interactive Heatmap & POI Canvas Layer
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = zoomScale
                    scaleY = zoomScale
                    translationX = panOffset.x
                    translationY = panOffset.y
                }
                .pointerInput(heatZones, pois, visiblePoiTypes, zoomScale, panOffset) {
                    detectTapGestures { tapOffset ->
                        val width = size.width
                        val height = size.height

                        // Check POI clicks
                        val clickedPoi = pois.firstOrNull { poi ->
                            if (visiblePoiTypes.contains(poi.type)) {
                                val poiOffset = Offset(poi.xPercent * width, poi.yPercent * height)
                                (tapOffset - poiOffset).getDistance() < (45f / zoomScale.coerceAtLeast(1f))
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

            // 1. Draw Crowd Heat Zones with Glow Gradients directly over the Masterplan
            heatZones.forEach { zone ->
                val center = Offset(zone.relativeX * w, zone.relativeY * h)
                val radius = zone.radiusRatio * w
                val baseColor = Color(zone.density.colorHex)

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            baseColor.copy(alpha = 0.75f),
                            baseColor.copy(alpha = 0.35f),
                            Color.Transparent
                        ),
                        center = center,
                        radius = radius
                    ),
                    center = center,
                    radius = radius
                )

                // Inner core pulsing indicator
                drawCircle(
                    color = baseColor,
                    center = center,
                    radius = 10f
                )
                drawCircle(
                    color = Color.White,
                    center = center,
                    radius = 4f
                )
            }

            // 2. Draw POI Badges over the Masterplan
            pois.forEach { poi ->
                if (visiblePoiTypes.contains(poi.type)) {
                    val center = Offset(poi.xPercent * w, poi.yPercent * h)
                    val badgeColor = when (poi.type) {
                        POIType.ENTRY_GATE -> StatusGreen
                        POIType.EXIT_GATE  -> StatusOrange
                        POIType.PARKING    -> SandstoneGold
                        POIType.WASHROOM   -> Color(0xFF0288D1)
                        POIType.MEDICAL    -> StatusRed
                        POIType.WATER      -> Color(0xFF00ACC1)
                        POIType.SECURITY   -> Color(0xFFAB47BC)
                    }

                    // Outer halo badge
                    drawCircle(
                        color = Color.Black.copy(alpha = 0.6f),
                        center = center,
                        radius = 22f
                    )
                    drawCircle(
                        color = badgeColor,
                        center = center,
                        radius = 18f
                    )
                    drawCircle(
                        color = Color.White,
                        center = center,
                        radius = 12f
                    )
                    drawCircle(
                        color = badgeColor,
                        center = center,
                        radius = 7f
                    )
                }
            }
        }
    }
}

