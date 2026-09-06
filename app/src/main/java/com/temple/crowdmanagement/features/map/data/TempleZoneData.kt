package com.temple.crowdmanagement.features.map.data

import com.temple.crowdmanagement.core.model.*

object TempleZoneData {

    fun getZonesForTemple(temple: TempleSite): List<HeatZone> {
        return when (temple) {
            TempleSite.SOMNATH -> listOf(
                HeatZone("Somnath Sanctum & Garbhagriha", CrowdDensityLevel.CRITICAL, 2400, 2500, 0.20f, 0.58f, 0.12f),
                HeatZone("Visitors Plaza & Concourse",     CrowdDensityLevel.HEAVY,    1850, 2200, 0.28f, 0.52f, 0.10f),
                HeatZone("East Entry Promenade",           CrowdDensityLevel.MODERATE,  950, 1500, 0.45f, 0.44f, 0.09f),
                HeatZone("Central Parking & Arrival Bay",  CrowdDensityLevel.SMOOTH,    420, 1200, 0.65f, 0.44f, 0.11f)
            )
            TempleSite.DWARKA -> listOf(
                HeatZone("Dwarkadhish Main Sanctum",      CrowdDensityLevel.CRITICAL, 2100, 2200, 0.64f, 0.35f, 0.13f),
                HeatZone("Swarg Dwar Entry Queue",        CrowdDensityLevel.HEAVY,    1450, 1800, 0.66f, 0.52f, 0.11f),
                HeatZone("Gomti Ghat Sacred Steps",       CrowdDensityLevel.MODERATE,  850, 1200, 0.37f, 0.22f, 0.10f),
                HeatZone("Moksha Dwar Exit Corridor",     CrowdDensityLevel.SMOOTH,    320,  900, 0.63f, 0.16f, 0.08f)
            )
            else -> listOf(
                HeatZone("Main Temple Sanctum",  CrowdDensityLevel.HEAVY,    1500, 1800, 0.50f, 0.40f, 0.15f),
                HeatZone("Outer Concourse",      CrowdDensityLevel.MODERATE,  700, 1200, 0.50f, 0.65f, 0.15f)
            )
        }
    }

    fun getPOIsForTemple(temple: TempleSite): List<ZonePOI> {
        return when (temple) {
            TempleSite.SOMNATH -> listOf(
                ZonePOI("S1",  "Somnath Garbhagriha",            POIType.ENTRY_GATE, 0.18f, 0.60f, "Main Sanctum & Jyotirlinga Darshan"),
                ZonePOI("S2",  "Visitors Plaza",                 POIType.ENTRY_GATE, 0.25f, 0.52f, "Main Concourse & Holding Area"),
                ZonePOI("S3",  "Digvijay Dwar (Gate 1)",         POIType.ENTRY_GATE, 0.35f, 0.46f, "Primary security screening entrance"),
                ZonePOI("S4",  "Beach Promenade Exit",           POIType.EXIT_GATE,  0.33f, 0.28f, "Scenic coastal walkway & exit"),
                ZonePOI("S5",  "Main Central Parking",           POIType.PARKING,    0.63f, 0.44f, "Main visitor parking · 650 bays"),
                ZonePOI("S6",  "Overflow East Parking",          POIType.PARKING,    0.80f, 0.68f, "Bus and heavy vehicle parking"),
                ZonePOI("S7",  "Tourist Facilitation Centre",    POIType.SECURITY,   0.53f, 0.88f, "Helpline, Cloakroom & Information"),
                ZonePOI("S8",  "SDRF Emergency Medical Unit 3",  POIType.MEDICAL,    0.42f, 0.58f, "Paramedics & coastal rescue post"),
                ZonePOI("S9",  "Drinking Water Station 1",       POIType.WATER,      0.28f, 0.60f, "RO filtered cold water"),
                ZonePOI("S10", "Public Restrooms Block",         POIType.WASHROOM,   0.36f, 0.38f, "Accessible hygiene facilities")
            )
            TempleSite.DWARKA -> listOf(
                ZonePOI("D1",  "Dwarkadhish Main Shrine",        POIType.ENTRY_GATE, 0.64f, 0.34f, "Jagat Mandir Sacred Sanctum"),
                ZonePOI("D2",  "Swarg Dwar (Main Entry)",        POIType.ENTRY_GATE, 0.66f, 0.53f, "Pilgrim entry with queue barriers"),
                ZonePOI("D3",  "Moksha Dwar (North Exit)",       POIType.EXIT_GATE,  0.63f, 0.15f, "North clearance gate towards market"),
                ZonePOI("D4",  "Gomti Ghat Holy Dip",            POIType.WATER,      0.37f, 0.20f, "River bathing ghat and rituals"),
                ZonePOI("D5",  "Singh Dwar & Security Check",    POIType.SECURITY,   0.22f, 0.28f, "Police & RFID scanner checkpoint"),
                ZonePOI("D6",  "Bet Dwarka Ferry Point",         POIType.ENTRY_GATE, 0.19f, 0.45f, "Ferry boat transport terminal"),
                ZonePOI("D7",  "Pilgrim Vehicle Parking",        POIType.PARKING,    0.90f, 0.28f, "Designated multi-tier parking"),
                ZonePOI("D8",  "Medical First Aid Post",         POIType.MEDICAL,    0.72f, 0.45f, "Emergency response & stretchers"),
                ZonePOI("D9",  "Sanitation & Restroom Complex",  POIType.WASHROOM,   0.80f, 0.50f, "Clean washrooms & drinking water")
            )
            else -> listOf(
                ZonePOI("P1", "Main Entry Gate",  POIType.ENTRY_GATE, 0.50f, 0.85f, "Devotee entry point"),
                ZonePOI("P2", "Main Exit Gate",   POIType.EXIT_GATE,  0.50f, 0.15f, "Devotee exit clearance")
            )
        }
    }
}
