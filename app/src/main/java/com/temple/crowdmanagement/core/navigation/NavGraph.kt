package com.temple.crowdmanagement.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.temple.crowdmanagement.features.auth.LoginScreen
import com.temple.crowdmanagement.features.auth.SignupScreen
import com.temple.crowdmanagement.features.auth.LanguageScreen
import com.temple.crowdmanagement.features.booking.presentation.BookingScreen
import com.temple.crowdmanagement.features.dashboard.presentation.DashboardScreen
import com.temple.crowdmanagement.features.emergency.presentation.EmergencySOSScreen
import com.temple.crowdmanagement.features.guide.presentation.GuideScreen
import com.temple.crowdmanagement.features.map.presentation.LiveTempleMapScreen
import com.temple.crowdmanagement.features.profile.presentation.ProfileScreen
import com.temple.crowdmanagement.features.queue.presentation.SmartQueueScreen
import com.temple.crowdmanagement.ui.theme.*

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

@Composable
fun MainAppContainer() {
    val navController = rememberNavController()
    var isAuthenticated by remember { mutableStateOf(false) }

    // Clean 4-item nav (Guide lives inside Dashboard quick actions)
    val bottomNavItems = listOf(
        BottomNavItem("dashboard", "Home",    Icons.Default.Home),
        BottomNavItem("map",       "Map",     Icons.Default.Map),
        BottomNavItem("queue",     "Queue",   Icons.Default.ConfirmationNumber),
        BottomNavItem("booking",   "Booking", Icons.Default.QrCode2),
        BottomNavItem("profile",   "Profile", Icons.Default.Person)
    )

    val bottomNavRoutes = bottomNavItems.map { it.route }.toSet() + setOf("guide")

    Scaffold(
        floatingActionButton = {
            if (isAuthenticated) {
                FloatingActionButton(
                    onClick = { navController.navigate("emergency") },
                    containerColor = StatusRed,
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier.size(62.dp)
                ) {
                    Icon(
                        Icons.Default.Sos,
                        contentDescription = "Emergency SOS",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            if (isAuthenticated) {
                NavigationBar(
                    containerColor = CardDarkBg,
                    contentColor   = SandstoneGold,
                    tonalElevation = 0.dp
                ) {
                    val currentRoute =
                        navController.currentBackStackEntryAsState().value?.destination?.route

                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState    = true
                                }
                            },
                            icon = {
                                Icon(
                                    item.icon,
                                    contentDescription = item.title
                                )
                            },
                            label = {
                                Text(
                                    item.title,
                                    fontSize = 10.sp,
                                    fontWeight = if (currentRoute == item.route) FontWeight.Bold
                                                 else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor         = SaffronPrimary,
                                selectedIconColor      = Color.White,
                                unselectedIconColor    = TextSecondary,
                                selectedTextColor      = SaffronPrimary,
                                unselectedTextColor    = TextSecondary
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (isAuthenticated) "dashboard" else "auth",
            modifier = Modifier.padding(innerPadding)
        ) {
            // ============ AUTH ROUTES ============
            composable("auth") {
                var screen by remember { mutableStateOf("login") }
                when (screen) {
                    "login" -> LoginScreen(
                        onLoginSuccess = {
                            isAuthenticated = true
                            navController.navigate("dashboard") {
                                popUpTo("auth") { inclusive = true }
                            }
                        },
                        onNavigateToSignUp     = { screen = "signup" },
                        onNavigateToLanguage   = { screen = "language" }
                    )
                    "signup" -> SignupScreen(
                        onSignUpSuccess = {
                            isAuthenticated = true
                            navController.navigate("dashboard") {
                                popUpTo("auth") { inclusive = true }
                            }
                        },
                        onNavigateToLogin = { screen = "login" }
                    )
                    "language" -> LanguageScreen(
                        onLanguageSelected = { screen = "login" },
                        onBack             = { screen = "login" }
                    )
                }
            }

            // ============ MAIN SCREENS ============
            composable("dashboard") {
                DashboardScreen(
                    onLiveMapClick      = { navController.navigate("map")       },
                    onBookDarshanClick  = { navController.navigate("booking")   },
                    onSOSClick          = { navController.navigate("emergency") },
                    onTempleGuideClick  = { navController.navigate("guide")     }
                )
            }

            composable("map")       { LiveTempleMapScreen() }
            composable("queue")     { SmartQueueScreen()    }
            composable("booking")   { BookingScreen()       }
            composable("guide")     { GuideScreen()         }
            composable("profile")   { ProfileScreen()       }
            composable("emergency") { EmergencySOSScreen()  }
        }
    }
}