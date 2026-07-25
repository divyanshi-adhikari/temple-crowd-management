package com.temple.crowdmanagement.core.navigation

sealed class Screen(val route: String, val title: String) {
    // Auth
    object Auth : Screen("auth", "Auth")
    
    // Developer 2 (Action Systems)
    object Map : Screen("map", "Map")
    object Queue : Screen("queue", "Queue")
    object Booking : Screen("booking", "Booking")
    object Emergency : Screen("emergency", "Emergency")
    
    // Developer 1 (Core Pilgrim Journey)
    object Dashboard : Screen("dashboard", "Home")
    object Guide : Screen("guide", "Guide")
    object Profile : Screen("profile", "Profile")
}