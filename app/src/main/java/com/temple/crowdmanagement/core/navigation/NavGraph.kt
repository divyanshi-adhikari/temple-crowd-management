package com.temple.crowdmanagement.core.navigation

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.temple.crowdmanagement.features.auth.AuthViewModel
import com.temple.crowdmanagement.features.auth.AuthViewModelFactory
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
import com.temple.crowdmanagement.features.security.presentation.SecurityAuthViewModel
import com.temple.crowdmanagement.features.security.presentation.SecurityAuthViewModelFactory
import com.temple.crowdmanagement.features.security.presentation.SecurityDashboardScreen
import com.temple.crowdmanagement.features.security.presentation.SecurityNotificationScreen
import com.temple.crowdmanagement.ui.theme.*

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

@Composable
fun MainAppContainer() {
    val navController = rememberNavController()
    val context = LocalContext.current
    
    val pilgrimViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(context)
    )
    
    val securityViewModel: SecurityAuthViewModel = viewModel(
        factory = SecurityAuthViewModelFactory(context)
    )
    
    val isPilgrimLoggedIn by pilgrimViewModel.isLoggedIn.collectAsState()
    val isSecurityLoggedIn by securityViewModel.isLoggedIn.collectAsState()
    
    val isAuthenticated = isPilgrimLoggedIn || isSecurityLoggedIn
    val isSecurityAuthenticated = isSecurityLoggedIn

    // ✅ Bottom Navigation Items (Pilgrim Only)
    val bottomNavItems = listOf(
        BottomNavItem("dashboard", "Home", Icons.Default.Home),
        BottomNavItem("map", "Map", Icons.Default.Map),
        BottomNavItem("queue", "Queue", Icons.Default.ConfirmationNumber),
        BottomNavItem("booking", "Booking", Icons.Default.QrCode2),
        BottomNavItem("profile", "Profile", Icons.Default.Person)
    )

    Scaffold(
        floatingActionButton = {
            if (isAuthenticated && !isSecurityAuthenticated) {
                FloatingActionButton(
                    onClick = { navController.navigate("emergency") },
                    containerColor = StatusRed,
                    contentColor = Color.Black,
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
            if (isAuthenticated && !isSecurityAuthenticated) {
                NavigationBar(
                    containerColor = CardDarkBg,
                    contentColor = GoldAccent,
                    tonalElevation = 0.dp
                ) {
                    val currentRoute =
                        navController.currentBackStackEntryAsState().value?.destination?.route

                    bottomNavItems.forEach { item ->
                        val isSelected = currentRoute == item.route
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    item.icon,
                                    contentDescription = item.title,
                                    tint = if (isSelected) SaffronPrimary else TextSecondary
                                )
                            },
                            label = {
                                Text(
                                    item.title,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) SaffronPrimary else TextSecondary
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = SaffronPrimary,
                                selectedIconColor = Color.Black,
                                unselectedIconColor = TextSecondary,
                                selectedTextColor = SaffronPrimary,
                                unselectedTextColor = TextSecondary
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = when {
                isSecurityAuthenticated -> "security_dashboard"
                isAuthenticated -> "dashboard"
                else -> "auth"
            },
            modifier = Modifier.padding(innerPadding)
        ) {
            // ============ AUTH ROUTES ============
            composable("auth") {
                var screen by remember { mutableStateOf("login") }
                
                LaunchedEffect(Unit) {
                    pilgrimViewModel.clearSession()
                    securityViewModel.clearSession()
                }
                
                when (screen) {
                    "login" -> LoginScreen(
                        onLoginSuccess = {
                            navController.navigate("dashboard") {
                                popUpTo("auth") { inclusive = true }
                            }
                        },
                        onSecurityLoginSuccess = {
                            navController.navigate("security_dashboard") {
                                popUpTo("auth") { inclusive = true }
                            }
                        },
                        onNavigateToSignUp = { screen = "signup" },
                        onNavigateToLanguage = { screen = "language" }
                    )
                    "signup" -> SignupScreen(
                        onSignUpSuccess = {
                            navController.navigate("dashboard") {
                                popUpTo("auth") { inclusive = true }
                            }
                        },
                        onNavigateToLogin = { screen = "login" }
                    )
                    "language" -> LanguageScreen(
                        onLanguageSelected = { screen = "login" },
                        onBack = { screen = "login" }
                    )
                }
            }

            // ============ PILGRIM MAIN SCREENS ============
            composable("dashboard") {
                DashboardScreen(
                    onLogout = {
                        pilgrimViewModel.logout()
                        navController.navigate("auth") {
                            popUpTo("dashboard") { inclusive = true }
                        }
                    },
                    onLiveMapClick = { navController.navigate("map") },
                    onBookDarshanClick = { navController.navigate("booking") },
                    onSOSClick = { navController.navigate("emergency") },
                    onTempleGuideClick = { navController.navigate("guide") }
                )
            }

            composable("map") { LiveTempleMapScreen() }
            composable("queue") { SmartQueueScreen() }
            composable("booking") { BookingScreen() }
            composable("guide") { GuideScreen() }
            
            composable("profile") { 
                ProfileScreen(
                    onLogout = {
                        pilgrimViewModel.logout()
                        navController.navigate("auth") {
                            popUpTo("dashboard") { inclusive = true }
                        }
                    },
                    onSettingsClick = {
                        // Placeholder for Settings
                    },
                    onEditProfileClick = {
                        // Placeholder for Edit Profile
                    }
                )
            }
            
            composable("emergency") { EmergencySOSScreen() }

            // ============ SECURITY ROUTES ============
            composable("security_dashboard") {
                SecurityDashboardScreen(
                    onLogout = {
                        securityViewModel.logout()
                        navController.navigate("auth") {
                            popUpTo("security_dashboard") { inclusive = true }
                        }
                    },
                    onNotificationsClick = {
                        navController.navigate("security_notifications")
                    }
                )
            }

            composable("security_notifications") {
                SecurityNotificationScreen(
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}