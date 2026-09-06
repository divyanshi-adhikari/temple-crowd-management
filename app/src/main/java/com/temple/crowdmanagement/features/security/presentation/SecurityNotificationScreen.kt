package com.temple.crowdmanagement.features.security.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.temple.crowdmanagement.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityNotificationScreen(
    onBack: () -> Unit,
    viewModel: SecurityNotificationViewModel = viewModel()
) {
    val notifications by viewModel.notifications.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val unreadCount by viewModel.unreadCount.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadNotifications()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "🔔 Notifications",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = SaffronPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = SaffronPrimary
                        )
                    }
                },
                actions = {
                    if (unreadCount > 0) {
                        Badge(
                            containerColor = SaffronPrimary,
                            modifier = Modifier.offset(x = (-8).dp, y = 8.dp)
                        ) {
                            Text(
                                text = if (unreadCount > 99) "99+" else unreadCount.toString(),
                                fontSize = 10.sp,
                                color = TextPrimary
                            )
                        }
                    }
                    IconButton(onClick = { viewModel.markAllAsRead() }) {
                        Icon(
                            Icons.Default.DoneAll,
                            contentDescription = "Mark All Read",
                            tint = SaffronPrimary
                        )
                    }
                    IconButton(onClick = { viewModel.loadNotifications() }) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Refresh",
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
                        text = "Loading notifications... 🔔",
                        color = TextSecondary,
                        fontSize = 14.sp
                    )
                }
            }
        } else if (notifications.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "🔕",
                        fontSize = 64.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No Notifications",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "You're all caught up!",
                        fontSize = 14.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Refresh to check for new updates",
                        fontSize = 12.sp,
                        color = TextSecondary.copy(alpha = 0.6f)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(SpiritualDarkBg),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${notifications.size} Notifications",
                            fontSize = 13.sp,
                            color = TextSecondary
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilterChip(
                                selected = true,
                                onClick = { /* TODO: Filter by all */ },
                                label = { Text("All", fontSize = 12.sp) },
                                modifier = Modifier.height(32.dp)
                            )
                            FilterChip(
                                selected = false,
                                onClick = { /* TODO: Filter by unread */ },
                                label = { Text("Unread", fontSize = 12.sp) },
                                modifier = Modifier.height(32.dp)
                            )
                        }
                    }
                }

                items(
                    items = notifications,
                    key = { it.id }
                ) { notification ->
                    NotificationCard(
                        notification = notification,
                        onAcknowledge = { notificationId ->
                            viewModel.acknowledgeNotification(notificationId)
                        },
                        onDismiss = { notificationId ->
                            viewModel.dismissNotification(notificationId)
                        }
                    )
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "You've seen all notifications",
                            fontSize = 12.sp,
                            color = TextSecondary.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    }
}

// ============ NOTIFICATION CARD ============
@Composable
fun NotificationCard(
    notification: NotificationItem,
    onAcknowledge: (String) -> Unit,
    onDismiss: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp), clip = false)
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isRead)
                SpiritualDarkBg
            else
                SaffronPrimary.copy(alpha = 0.08f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.Top
                ) {
                    Surface(
                        shape = CircleShape,
                        color = when (notification.type) {
                            "emergency" -> DangerRed.copy(alpha = 0.15f)
                            "security" -> SaffronPrimary.copy(alpha = 0.15f)
                            "medical" -> SuccessGreen.copy(alpha = 0.15f)
                            else -> SandstoneGold.copy(alpha = 0.15f)
                        },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = when (notification.type) {
                                    "emergency" -> "🚨"
                                    "security" -> "🔒"
                                    "medical" -> "🏥"
                                    else -> "📢"
                                },
                                fontSize = 18.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = notification.title,
                                fontSize = 15.sp,
                                fontWeight = if (notification.isRead) FontWeight.Medium else FontWeight.Bold,
                                color = if (notification.isRead) TextSecondary else TextPrimary,
                                maxLines = if (expanded) Int.MAX_VALUE else 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (!notification.isRead) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(SaffronPrimary, CircleShape)
                                )
                            }
                        }

                        Text(
                            text = notification.timestamp,
                            fontSize = 11.sp,
                            color = TextSecondary
                        )

                        if (expanded) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = notification.message,
                                fontSize = 14.sp,
                                color = TextPrimary,
                                lineHeight = 20.sp
                            )

                            notification.location?.let {
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.LocationOn,
                                        contentDescription = "Location",
                                        tint = SaffronPrimary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = it,
                                        fontSize = 12.sp,
                                        color = SaffronLight
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = when (notification.priority) {
                                    "high" -> DangerRed.copy(alpha = 0.15f)
                                    "medium" -> WarningYellow.copy(alpha = 0.15f)
                                    else -> SaffronPrimary.copy(alpha = 0.15f)
                                }
                            ) {
                                Text(
                                    text = "Priority: ${notification.priority.uppercase()}",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = when (notification.priority) {
                                        "high" -> DangerRed
                                        "medium" -> WarningYellow
                                        else -> SaffronPrimary
                                    },
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (!notification.isRead) {
                                    Button(
                                        onClick = { onAcknowledge(notification.id) },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = SaffronPrimary
                                        ),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(
                                            Icons.Default.Check,
                                            contentDescription = "Acknowledge",
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Acknowledge",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimary
                                        )
                                    }
                                }
                                OutlinedButton(
                                    onClick = { onDismiss(notification.id) },
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "Dismiss",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = TextSecondary
                                    )
                                }
                            }
                        } else {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = notification.message,
                                fontSize = 13.sp,
                                color = TextSecondary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                Icon(
                    if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = TextSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

// ============ NOTIFICATION ITEM DATA CLASS ============
data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val type: String,
    val priority: String,
    val timestamp: String,
    val location: String? = null,
    val isRead: Boolean = false,
    val templeId: String,
    val fromController: String? = null
)