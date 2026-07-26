package com.temple.crowdmanagement.features.guide.repository

import com.temple.crowdmanagement.features.guide.model.GuideData
import com.temple.crowdmanagement.features.guide.model.AartiTiming
import com.temple.crowdmanagement.features.guide.model.FAQ
import com.temple.crowdmanagement.features.guide.model.Contact
import com.temple.crowdmanagement.features.guide.model.QuickFact
import kotlinx.coroutines.delay

class GuideRepository {
    
    suspend fun getGuideData(): GuideData {
        delay(800)
        
        return GuideData(
            templeName = "Dwarkadhish Temple",
            location = "Dwarka, Gujarat",
            description = "Dwarkadhish Temple, also known as Jagat Mandir, is one of the most sacred Hindu temples dedicated to Lord Krishna. Located in Dwarka, Gujarat, it is one of the four Char Dham pilgrimage sites.",
            history = "The temple was built in the 16th century and is believed to be the site of the ancient city of Dwarka, the capital of Lord Krishna's kingdom.",
            architecture = "The temple is built in the Chalukya style of architecture. It stands on a 72-column platform and features a 5-story shikhara.",
            timings = "6:30 AM - 9:30 PM",
            aartiTimings = listOf(
                AartiTiming("Mangala Aarti", "5:30 AM", "First Aarti of the day", false),
                AartiTiming("Shringar Aarti", "7:00 AM", "Decorating the deity", false),
                AartiTiming("Rajbhog Aarti", "11:00 AM", "Royal offering to the Lord", false),
                AartiTiming("Sandhya Aarti", "7:00 PM", "Evening Aarti with lamps", false),
                AartiTiming("Shayan Aarti", "9:30 PM", "Night Aarti before sleeping", true)
            ),
            faqs = listOf(
                FAQ("What are the temple timings?", "The temple is open from 6:30 AM to 9:30 PM daily."),
                FAQ("Is photography allowed inside?", "Photography is not allowed inside the main sanctum."),
                FAQ("What should I wear?", "Traditional attire is recommended. Shoulders and knees should be covered."),
                FAQ("How to reach Dwarkadhish Temple?", "Dwarka is well-connected by road, rail, and air."),
                FAQ("Are there any special darshan tickets?", "Yes, VIP/Special darshan tickets are available.")
            ),
            contacts = listOf(
                Contact("Temple Office", "+91 12345 67890", "🏛️"),
                Contact("Enquiry", "+91 98765 43210", "📞"),
                Contact("Email", "info@dwarkadhish.org", "✉️"),
                Contact("Website", "www.dwarkadhish.org", "🌐")
            ),
            quickFacts = listOf(
                
                QuickFact(label = "Deity", value = "Krishna", icon = "🕉️"),
                QuickFact(label = "Temple", value = "Jagat ", icon = "🏛️"),
                QuickFact(label = "Established", value = "16th C", icon = "📅"),
                QuickFact(label = "Char Dham", value = "West", icon = "⭐"),
                QuickFact(label = "Location", value = "Dwarka", icon = "📍"),
                QuickFact(label = "Railway", value = "Dwarka Jn", icon = "🚉"),
                QuickFact(label = "Airport", value = "Jamnagar", icon = "✈️"),
                QuickFact(label = "River", value = "Gomti Ghat", icon = "🌊")
            )
        )
    }
}