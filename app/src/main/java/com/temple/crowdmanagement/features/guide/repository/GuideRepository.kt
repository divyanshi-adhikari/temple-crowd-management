package com.temple.crowdmanagement.features.guide.repository

import com.temple.crowdmanagement.core.model.TempleSite
import com.temple.crowdmanagement.core.network.NetworkClient
import com.temple.crowdmanagement.features.guide.model.GuideData
import com.temple.crowdmanagement.features.guide.model.AartiTiming
import com.temple.crowdmanagement.features.guide.model.FAQ
import com.temple.crowdmanagement.features.guide.model.Contact
import com.temple.crowdmanagement.features.guide.model.QuickFact

class GuideRepository {
    
    suspend fun getGuideData(templeSite: TempleSite = TempleSite.SOMNATH): GuideData {
        // Try live backend first
        val remoteTemples = try { NetworkClient.getTemples() } catch (_: Exception) { emptyList() }
        val target = remoteTemples.find { it.id.equals(templeSite.backendId, ignoreCase = true) }

        return when (templeSite) {
            TempleSite.SOMNATH -> GuideData(
                templeName = target?.fullName ?: "Shree Somnath Jyotirlinga Temple",
                location = target?.city ?: "Prabhas Patan, Veraval, Gujarat",
                description = "First among the Twelve Sacred Jyotirlingas of Lord Shiva. Somnath is celebrated as the eternal shrine, standing with majesty on the western sea coast of Saurashtra.",
                history = "Reconstructed multiple times after ancient invasions, the current Kailash Mahameru Prasad temple was initiated by Sardar Vallabhbhai Patel and inaugurated by Dr. Rajendra Prasad in 1951.",
                architecture = "Built in the grand Chalukya (Kailash Mahameru Prasad) architectural style, featuring an imposing 155-ft spire, intricate stone pillars, and the sacred Digvijay Dwar.",
                timings = "06:00 AM - 09:30 PM",
                aartiTimings = target?.aartis?.map {
                    AartiTiming(it.name, it.time, "Sacred divine offering", it.name.contains("Shayan", true))
                } ?: listOf(
                    AartiTiming("Pratah Mangla Aarti", "07:00 AM", "Morning Mangla & Snan", false),
                    AartiTiming("Madhyahna Shringar Aarti", "12:00 PM", "Mid-day Shringar & Bhog", false),
                    AartiTiming("Sandhya Maha Aarti", "07:00 PM", "Evening Aarti with deepdarshan", false),
                    AartiTiming("Shayan Aarti", "09:30 PM", "Night Aarti before temple closure", true)
                ),
                faqs = listOf(
                    FAQ("What is the dress code?", "Traditional Indian attire. Shorts, sleeveless clothing, and western beachwear are strictly prohibited."),
                    FAQ("Is photography allowed?", "Photography is strictly prohibited inside the main sanctum. Cloakrooms for mobile phones are at Digvijay Dwar."),
                    FAQ("Are wheelchairs available?", "Yes, complimentary wheelchairs and battery buggies are available at Gate 4 for senior citizens."),
                    FAQ("What is the Sound & Light show time?", "The Light & Sound show takes place daily at 8:00 PM near the sea promenade.")
                ),
                contacts = listOf(
                    Contact("Control Room", target?.emergencyContact ?: "+91 2876 231200", "🏛️"),
                    Contact("Police In-charge", target?.policeIncharge ?: "SP Gir Somnath (Cmd. S. V. Jadeja)", "👮"),
                    Contact("Emergency / Rescue", target?.sdrfTeam ?: "SDRF Marine Battalion 3 / 112", "🚑"),
                    Contact("Official Portal", "www.somnath.org", "🌐")
                ),
                quickFacts = listOf(
                    QuickFact("Deity", "Lord Shiva", "🕉️"),
                    QuickFact("Significance", "1st Jyotirlinga", "⭐"),
                    QuickFact("Architecture", "Chalukya", "🏛️"),
                    QuickFact("Coast", "Arabian Sea", "🌊"),
                    QuickFact("Capacity", "${target?.dailyCapacity ?: 75000}", "👥"),
                    QuickFact("Nearest Rail", "Veraval Jn", "🚉")
                )
            )

            TempleSite.DWARKA -> GuideData(
                templeName = target?.fullName ?: "Shree Dwarkadhish Jagat Mandir",
                location = target?.city ?: "Dwarka, Gujarat",
                description = "One of the supreme Char Dham pilgrimage sites, dedicated to Lord Krishna as the King of Dwarka (Dwarkadhish).",
                history = "Believed to have been originally established over 2,500 years ago by Vajranabha, great-grandson of Lord Krishna, at the holy confluence of the Gomti river.",
                architecture = "Rising five storeys on 72 ornate limestone pillars, crowned by the grand 52-yard sacred flag (Dhwajaji).",
                timings = "06:30 AM - 09:30 PM",
                aartiTimings = listOf(
                    AartiTiming("Mangla Aarti & Snan", "06:30 AM", "Morning darshan", false),
                    AartiTiming("Shringar & Gwal Bhog", "10:30 AM", "Royal adornment", false),
                    AartiTiming("Sandhya Aarti", "07:30 PM", "Evening Aarti", false),
                    AartiTiming("Shayan Aarti & Dhwajaji", "08:30 PM", "Night closing Aarti", true)
                ),
                faqs = listOf(
                    FAQ("What is the dress code?", "Modest traditional attire. Shoulders and knees must be fully covered."),
                    FAQ("Where is Gomti Ghat?", "Gomti Ghat is directly adjacent to the southern Swarga Dwar exit.")
                ),
                contacts = listOf(
                    Contact("Dwarka Mandir Office", "+91 2892 234080", "🏛️"),
                    Contact("Police Assistance", "DySP Dwarka (112)", "👮"),
                    Contact("Emergency Help", "108", "🚑")
                ),
                quickFacts = listOf(
                    QuickFact("Deity", "Lord Krishna", "🕉️"),
                    QuickFact("Pilgrimage", "Char Dham", "⭐"),
                    QuickFact("River", "Gomti Ghat", "🌊"),
                    QuickFact("Nearest Rail", "Dwarka Jn", "🚉")
                )
            )

            TempleSite.AMBAJI -> GuideData(
                templeName = target?.fullName ?: "Shree Arasuri Ambaji Mata Devasthan",
                location = target?.city ?: "Ambaji, Banaskantha, Gujarat",
                description = "One of the 51 sacred Shakti Peethas situated in the Arasur hills, revered as the Hridaya (Heart) Peetha of Maa Amba.",
                history = "Celebrated in Hindu scriptures for millenniums; the sanctum worships the divine Viso Yantra inscribed with sacred Vedic beeja mantras.",
                architecture = "White marble shrine with a magnificent golden Kalash soaring above the Gabbar hill pilgrimage track.",
                timings = "07:00 AM - 09:00 PM",
                aartiTimings = listOf(
                    AartiTiming("Pratah Mangla Aarti", "07:30 AM", "Morning prayers", false),
                    AartiTiming("Rajbhog & Thaal", "12:30 PM", "Noon offering", false),
                    AartiTiming("Sandhya Aarti", "07:00 PM", "Evening Aarti", false),
                    AartiTiming("Shayan Aarti", "09:00 PM", "Night closure", true)
                ),
                faqs = listOf(
                    FAQ("Is there a ropeway?", "Yes, the Gabbar Ropeway operates continuously from 7 AM to 7 PM.")
                ),
                contacts = listOf(
                    Contact("Ambaji Trust", "+91 2749 262136", "🏛️"),
                    Contact("Police & Security", "SP Banaskantha / 112", "👮")
                ),
                quickFacts = listOf(
                    QuickFact("Deity", "Maa Amba", "🕉️"),
                    QuickFact("Significance", "51 Shakti Peeth", "⭐"),
                    QuickFact("Hill", "Gabbar Arasur", "⛰️")
                )
            )

            TempleSite.PAVAGADH -> GuideData(
                templeName = target?.fullName ?: "Shree Kalika Mataji Temple",
                location = target?.city ?: "Pavagadh Hill, Champaner, Gujarat",
                description = "Historic Shakti Peetha situated on the volcanic peak of Pavagadh Hill, overlooking the UNESCO World Heritage site of Champaner.",
                history = "Ancient fortress shrine where the deity was worshipped for over a thousand years, recently renovated with a gilded shikhar and ropeway terminal.",
                architecture = "Hilltop fortified temple accessed via pilgrims stairs and the Machi ropeway ascent.",
                timings = "06:00 AM - 08:00 PM",
                aartiTimings = listOf(
                    AartiTiming("Maha Kalika Mangla Aarti", "06:00 AM", "Dawn prayers", false),
                    AartiTiming("Dhoop & Naivedya Aarti", "12:00 PM", "Noon rituals", false),
                    AartiTiming("Sandhya Deep Aarti", "06:30 PM", "Sunset Aarti", false),
                    AartiTiming("Temple Closure", "08:00 PM", "Night closure", true)
                ),
                faqs = listOf(
                    FAQ("How to reach the peak?", "Take the Machi Ropeway from lower base to terminal, then climb the remaining summit steps.")
                ),
                contacts = listOf(
                    Contact("Pavagadh Office", "+91 2676 245633", "🏛️"),
                    Contact("Emergency Rescue", "SDRF Ropeway Team / 112", "🚑")
                ),
                quickFacts = listOf(
                    QuickFact("Deity", "Maa Kali", "🕉️"),
                    QuickFact("Peak Height", "762 meters", "⛰️"),
                    QuickFact("Heritage", "UNESCO Champaner", "⭐")
                )
            )
        }
    }
}