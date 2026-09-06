package com.temple.crowdmanagement.features.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.temple.crowdmanagement.features.profile.components.*
import com.temple.crowdmanagement.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onLogout: () -> Unit = {},
    onSettingsClick: () -> Unit = {},        // ✅ NEW
    onEditProfileClick: () -> Unit = {},     // ✅ NEW
    viewModel: ProfileViewModel = viewModel()
) {
    val profileData by viewModel.profileData.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProfileData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "🛕 Devotee Dashboard",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = SaffronPrimary
                    )
                },
                actions = {
                    // ✅ Edit Profile - Now calls onEditProfileClick
                    IconButton(onClick = onEditProfileClick) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit Profile",
                            tint = SaffronPrimary
                        )
                    }
                    // ✅ Settings - Now calls onSettingsClick
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = SaffronPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SpiritualDarkBg
                )
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(
                        color = SaffronPrimary,
                        strokeWidth = 4.dp,
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Loading profile... 🙏",
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(SpiritualDarkBg),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Profile Header
                item {
                    ProfileHeader(
                        userName = profileData.userName,
                        userRole = profileData.userRole,
                        userEmail = profileData.userEmail
                    )
                }

                // Recent Bookings
                item {
                    RecentBookingCard(bookings = profileData.recentBookings)
                }

                // Visit Preferences
                item {
                    VisitPreferencesCard(preferences = profileData.visitPreferences)
                }

                // Quick Settings
                item {
                    QuickSettingsCard(settings = profileData.settings)
                }

                // Help Section
                item {
                    HelpSection()
                }

                // Logout Button
                item {
                    LogoutButton(
                        onLogout = onLogout
                    )
                }

                // Version
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "v1.0.0",
                            fontSize = 12.sp,
                            color = TextSecondary.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    }
}

// ============ LOGOUT BUTTON ============
@Composable
fun LogoutButton(
    onLogout: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onLogout() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = StatusRed.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Default.Logout,
                contentDescription = "Logout",
                tint = StatusRed,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Logout",
                color = StatusRed,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}