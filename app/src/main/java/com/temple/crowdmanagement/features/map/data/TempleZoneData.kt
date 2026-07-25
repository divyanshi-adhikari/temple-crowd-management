package com.temple.crowdmanagement.features.map.data

import com.temple.crowdmanagement.core.model.*

object TempleZoneData {

    fun getZonesForTemple(temple: TempleSite): List<HeatZone> {
        return when (temple) {
            TempleSite.SOMNATH -> listOf(
                HeatZone("Main Garbhagriha Entry", CrowdDensityLevel.CRITICAL, 2400, 2500, 0.5f, 0.35f, 0.22f),
                HeatZone("Outer Sabha Mandap", CrowdDensityLevel.HEAVY, 1200, 1500, 0.5f, 0.52f, 0.25f),
                HeatZone("South Courtyard", CrowdDensityLevel.MODERATE, 450, 1000, 0.28f, 0.65f, 0.18f),
                HeatZone("North Exit Corridor", CrowdDensityLevel.SMOOTH, 150, 800, 0.72f, 0.65f, 0.16f)
            )
            TempleSite.DWARKA -> listOf(
                HeatZone("Moksha Dwara Gate", CrowdDensityLevel.HEAVY, 1800, 2000, 0.35f, 0.40f, 0.20f),
                HeatZone("Swarga Dwara Exit", CrowdDensityLevel.SMOOTH, 200, 1500, 0.68f, 0.40f, 0.18f),
                HeatZone("Inner Sanctum", CrowdDensityLevel.CRITICAL, 3100, 3200, 0.50f, 0.25f, 0.24f)
            )
            TempleSite.AMBAJI -> listOf(
                HeatZone("Main Gate Chawk", CrowdDensityLevel.HEAVY, 1900, 2200, 0.50f, 0.30f, 0.25f),
                HeatZone("Prasad Distribution Line", CrowdDensityLevel.MODERATE, 750, 1200, 0.30f, 0.60f, 0.20f),
                HeatZone("Parking Shuttle Stand", CrowdDensityLevel.SMOOTH, 120, 1000, 0.75f, 0.75f, 0.15f)
            )
            TempleSite.PAVAGADH -> listOf(
                HeatZone("Ropeway Upper Station", CrowdDensityLevel.CRITICAL, 1500, 1500, 0.45f, 0.20f, 0.22f),
                HeatZone("Stairway Queue Step 300", CrowdDensityLevel.HEAVY, 1100, 1200, 0.50f, 0.55f, 0.20f),
                HeatZone("Lower Parking Hub", CrowdDensityLevel.MODERATE, 600, 1500, 0.50f, 0.82f, 0.18f)
            )
        }
    }

    fun getPOIsForTemple(temple: TempleSite): List<ZonePOI> {
        return listOf(
            ZonePOI("P1", "East Entry Gate (Gate 1)", POIType.ENTRY_GATE, 0.50f, 0.88f, "Main devotial entry point"),
            ZonePOI("P2", "North Exit Gate (Gate 2)", POIType.EXIT_GATE, 0.85f, 0.50f, "High speed exit clearance"),
            ZonePOI("P3", "Intelligent Parking P1", POIType.PARKING, 0.15f, 0.85f, "Capacity: 450 vehicles available"),
            ZonePOI("P4", "Primary Washroom Block", POIType.WASHROOM, 0.18f, 0.50f, "Clean & Accessible facilities"),
            ZonePOI("P5", "Emergency Medical Post 1", POIType.MEDICAL, 0.80f, 0.30f, "2 Ambulance units stationed"),
            ZonePOI("P6", "Free Drinking Water Station", POIType.WATER, 0.50f, 0.70f, "RO filtered cold water")
        )
    }
}
