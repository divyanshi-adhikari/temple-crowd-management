package com.temple.crowdmanagement.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.temple.crowdmanagement.features.auth.presentation.AuthScreen
import com.temple.crowdmanagement.features.booking.presentation.BookingScreen
import com.temple.crowdmanagement.features.dashboard.presentation.DashboardScreen
import com.temple.crowdmanagement.features.emergency.presentation.EmergencySOSScreen
import com.temple.crowdmanagement.features.guide.presentation.GuideScreen
import com.temple.crowdmanagement.features.map.presentation.LiveTempleMapScreen
import com.temple.crowdmanagement.features.profile.presentation.ProfileScreen
import com.temple.crowdmanagement.features.queue.presentation.SmartQueueScreen
import com.temple.crowdmanagement.ui.theme.*

data class BottomNavItem(
    val screen: Screen,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppContainer() {
    val navController = rememberNavController()

    val bottomNavItems = listOf(
        // Developer 2 (Action Systems)
        BottomNavItem(Screen.Map, Icons.Default.Map),
        BottomNavItem(Screen.Queue, Icons.Default.ConfirmationNumber),
        BottomNavItem(Screen.Booking, Icons.Default.QrCode2),
        BottomNavItem(Screen.Emergency, Icons.Default.Sos),
        // Developer 1 (Core Pilgrim Journey)
        BottomNavItem(Screen.Dashboard, Icons.Default.Home),
        BottomNavItem(Screen.Guide, Icons.Default.MenuBook),
        BottomNavItem(Screen.Profile, Icons.Default.Person)
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = SpiritualDarkBg,
                contentColor = SandstoneGold
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                bottomNavItems.forEach { item ->
                    val isSelected = currentRoute == item.screen.route
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            navController.navigate(item.screen.route) {
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
                                contentDescription = item.screen.title,
                                tint = if (isSelected) SaffronPrimary else TextSecondary
                            )
                        },
                        label = {
                            Text(
                                item.screen.title.take(8),
                                fontSize = 10.sp,
                                color = if (isSelected) SaffronPrimary else TextSecondary
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Map.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // DEV 2 ROUTES
            composable(Screen.Map.route) { LiveTempleMapScreen() }
            composable(Screen.Queue.route) { SmartQueueScreen() }
            composable(Screen.Booking.route) { BookingScreen() }
            composable(Screen.Emergency.route) { EmergencySOSScreen() }

            // DEV 1 ROUTES
            composable(Screen.Dashboard.route) { DashboardScreen() }
            composable(Screen.Guide.route) { GuideScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
            composable(Screen.Auth.route) { AuthScreen() }
        }
    }
}
