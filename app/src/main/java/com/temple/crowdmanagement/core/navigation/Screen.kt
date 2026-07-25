package com.temple.crowdmanagement.core.navigation

sealed class Screen(val route: String, val title: String, val developerOwner: String) {
    // Developer 1 Screens (Stubbed architecture)
    object Auth : Screen("auth", "Login / Signup", "DEV 1 WORKSPACE")
    object Dashboard : Screen("dashboard", "Home Dashboard", "DEV 1 WORKSPACE")
    object Guide : Screen("guide", "Pilgrim Guide", "DEV 1 WORKSPACE")
    object Profile : Screen("profile", "Profile & Settings", "DEV 1 WORKSPACE")

    // Developer 2 Screens (Fully Implemented)
    object Booking : Screen("booking", "Smart Darshan Pass", "DEV 2 WORKSPACE")
    object Map : Screen("map", "Live Crowd Heatmap", "DEV 2 WORKSPACE")
    object Queue : Screen("queue", "Smart Virtual Queue", "DEV 2 WORKSPACE")
    object Emergency : Screen("emergency", "SOS & Emergency", "DEV 2 WORKSPACE")
}
