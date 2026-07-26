package com.temple.crowdmanagement.features.guide.model

data class GuideData(
    val templeName: String = "Dwarkadhish Temple",
    val location: String = "Dwarka, Gujarat",
    val description: String = "",
    val history: String = "",
    val architecture: String = "",
    val timings: String = "",
    val aartiTimings: List<AartiTiming> = emptyList(),
    val faqs: List<FAQ> = emptyList(),
    val contacts: List<Contact> = emptyList(),
    val quickFacts: List<QuickFact> = emptyList()
)

data class AartiTiming(
    val name: String,
    val time: String,
    val description: String = "",
    val isSpecial: Boolean = false
)

data class FAQ(
    val question: String,
    val answer: String
)

data class Contact(
    val type: String,
    val value: String,
    val icon: String
)

data class QuickFact(
    val label: String,
    val value: String,
    val icon: String
)