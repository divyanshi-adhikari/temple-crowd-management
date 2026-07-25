package com.temple.crowdmanagement.features.dashboard.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.temple.crowdmanagement.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardTopBar() {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = "🛕 Dwarkadhish",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = SaffronPrimary
                )
                Text(
                    text = "Dwarka, Gujarat",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }
        },
        actions = {
            IconButton(onClick = { /* Notification */ }) {
                Badge(
                    containerColor = SaffronPrimary,
                    modifier = Modifier.offset(x = (-8).dp, y = 8.dp)
                ) {
                    Text("3", fontSize = 10.sp)
                }
                Icon(
                    Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = SaffronPrimary
                )
            }
            IconButton(onClick = { /* Profile */ }) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = SaffronPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = SpiritualDarkBg,
            titleContentColor = SaffronPrimary
        )
    )
}