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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.temple.crowdmanagement.features.security.components.*
import com.temple.crowdmanagement.features.security.model.DutyStatus
import com.temple.crowdmanagement.features.security.model.IncidentReport
import com.temple.crowdmanagement.features.security.model.IncidentType
import com.temple.crowdmanagement.features.security.model.IncidentPriority
import com.temple.crowdmanagement.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityDashboardScreen(
    onLogout: () -> Unit,
    onNotificationsClick: () -> Unit = {},
    viewModel: SecurityDashboardViewModel = viewModel()
) {
    val securityName by viewModel.securityName.collectAsState()
    val templeId by viewModel.templeId.collectAsState()
    val badgeId by viewModel.badgeId.collectAsState()
    val pendingAlerts by viewModel.pendingAlerts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val dutyInfo by viewModel.dutyInfo.collectAsState()
    val incidentReports by viewModel.incidentReports.collectAsState()

    // Dialog states
    var showIncidentDialog by remember { mutableStateOf(false) }
    var showDutyDialog by remember { mutableStateOf(false) }
    var showContactDialog by remember { mutableStateOf(false) }
    var showEmergencyDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadSecurityData()
    }

    // Emergency dialog
    if (showEmergencyDialog) {
        AlertDialog(
            onDismissRequest = { showEmergencyDialog = false },
            title = { Text("🚨 Emergency", color = DangerRed, fontWeight = FontWeight.Bold) },
            text = { 
                Column {
                    Text("Are you sure you want to trigger an emergency alert?")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "This will notify the controller immediately.",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.triggerEmergency()
                        showEmergencyDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DangerRed
                    )
                ) {
                    Text("Confirm Emergency")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEmergencyDialog = false }) {
                    Text("Cancel", color = TextSecondary)
                }
            }
        )
    }

    // Incident Report Dialog
    if (showIncidentDialog) {
        IncidentReportingDialog(
            onDismiss = { showIncidentDialog = false },
            onSubmit = { type, priority, title, description, location ->
                viewModel.reportIncident(
                    type = type,
                    priority = priority,
                    title = title,
                    description = description,
                    location = location
                )
            }
        )
    }

    // Duty Status Dialog
    if (showDutyDialog) {
        DutyStatusDialog(
            currentStatus = dutyInfo.status,
            onDismiss = { showDutyDialog = false },
            onStatusSelected = { status ->
                viewModel.updateDutyStatus(status)
            }
        )
    }

    // Contact Controller Dialog
    if (showContactDialog) {
        ContactControllerDialog(
            onDismiss = { showContactDialog = false },
            onSendMessage = { message ->
                viewModel.sendMessageToController(message)
            },
            onCall = {
                viewModel.callController()
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "🛡️ Security Dashboard",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = SaffronPrimary
                    )
                },
                actions = {
                    IconButton(onClick = { viewModel.loadSecurityData() }) {
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            tint = SaffronPrimary
                        )
                    }
                    IconButton(onClick = onLogout) {
                        Icon(
                            Icons.Default.Logout,
                            contentDescription = "Logout",
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
                        text = "Loading security data... 🛡️",
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
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Security Profile Card
                item {
                    SecurityProfileCard(
                        securityName = securityName,
                        templeId = templeId,
                        badgeId = badgeId
                    )
                }

                // ✅ Duty Status Card - Fixed
                item {
                    DutyStatusCard(
                        dutyInfo = dutyInfo,
                        onChangeStatus = { showDutyDialog = true }  // ✅ Fixed: onChangeStatus
                    )
                }

                // Stats Row
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatCard(
                            modifier = Modifier.weight(1f),
                            icon = "🔔",
                            value = pendingAlerts.size.toString(),
                            label = "Pending Alerts",
                            color = SaffronPrimary
                        )
                        StatCard(
                            modifier = Modifier.weight(1f),
                            icon = "📋",
                            value = incidentReports.size.toString(),
                            label = "Incidents",
                            color = GoldAccent
                        )
                        StatCard(
                            modifier = Modifier.weight(1f),
                            icon = "🛡️",
                            value = dutyInfo.status.displayName,
                            label = "Status",
                            color = when (dutyInfo.status) {
                                DutyStatus.ON_DUTY -> SuccessGreen
                                DutyStatus.ON_BREAK -> WarningYellow
                                DutyStatus.OFF_DUTY -> DangerRed
                            }
                        )
                    }
                }

                // ✅ Quick Actions Section
                item {
                    QuickActionsSection(
                        onReportIncident = { showIncidentDialog = true },
                        onContactController = { showContactDialog = true },
                        onEmergency = { showEmergencyDialog = true },
                        onNotifications = onNotificationsClick
                    )
                }

                // Section Header
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "📢 Recent Alerts",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "View All",
                            fontSize = 13.sp,
                            color = SaffronPrimary,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.clickable { onNotificationsClick() }  // ✅ Fixed
                        )
                    }
                }

                // Alert List
                if (pendingAlerts.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(4.dp, RoundedCornerShape(16.dp), clip = false),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = SpiritualDarkBg
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "🛡️",
                                    fontSize = 48.sp
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "No Alerts",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    text = "You're all caught up!",
                                    fontSize = 14.sp,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                } else {
                    items(pendingAlerts) { alert ->
                        SecurityAlertCard(
                            alert = alert,
                            onAcknowledge = { alertId ->
                                viewModel.acknowledgeAlert(alertId)
                            }
                        )
                    }
                }

                // Footer
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "DARSHAN Security v1.0.0",
                            fontSize = 12.sp,
                            color = TextSecondary.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    }
}

// ============ SECURITY PROFILE CARD ============
@Composable
fun SecurityProfileCard(
    securityName: String,
    templeId: String,
    badgeId: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(20.dp), clip = false),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = SpiritualDarkBg
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .size(60.dp)
                    .shadow(4.dp, CircleShape),
                shape = CircleShape,
                color = SaffronPrimary.copy(alpha = 0.15f)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        Icons.Default.Security,
                        contentDescription = "Security",
                        tint = SaffronPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = securityName.ifEmpty { "Security Personnel" },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Badge,
                        contentDescription = "Badge",
                        tint = TextSecondary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Badge: ${badgeId.ifEmpty { "---" }}",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = "Temple",
                        tint = TextSecondary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = templeId.ifEmpty { "---" },
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                }
            }
        }
    }
}

// ============ STAT CARD ============
@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    icon: String,
    value: String,
    label: String,
    color: Color
) {
    Card(
        modifier = modifier
            .shadow(4.dp, RoundedCornerShape(14.dp), clip = false),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = SpiritualDarkBg
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = icon,
                fontSize = 24.sp
            )
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = label,
                fontSize = 11.sp,
                color = TextSecondary
            )
        }
    }
}

// ============ SECURITY ALERT CARD ============
@Composable
fun SecurityAlertCard(
    alert: AlertData,
    onAcknowledge: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp), clip = false),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = SpiritualDarkBg
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = when (alert.type) {
                            "emergency" -> "🚨"
                            "security" -> "🔒"
                            "medical" -> "🏥"
                            else -> "📢"
                        },
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = alert.title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = alert.createdAt,
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = when (alert.priority) {
                        "high" -> DangerRed.copy(alpha = 0.15f)
                        "medium" -> WarningYellow.copy(alpha = 0.15f)
                        else -> SaffronPrimary.copy(alpha = 0.15f)
                    }
                ) {
                    Text(
                        text = alert.priority.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (alert.priority) {
                            "high" -> DangerRed
                            "medium" -> WarningYellow
                            else -> SaffronPrimary
                        },
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = alert.message,
                fontSize = 14.sp,
                color = TextSecondary,
                lineHeight = 20.sp
            )

            alert.location?.let {
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

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (alert.acknowledged) "✓ Acknowledged" else "⏳ Pending",
                    fontSize = 12.sp,
                    color = if (alert.acknowledged) SuccessGreen else WarningYellow
                )

                if (!alert.acknowledged) {
                    Button(
                        onClick = { onAcknowledge(alert.id) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SaffronPrimary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text(
                            text = "Acknowledge",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

// ============ ALERT DATA CLASS ============
data class AlertData(
    val id: String,
    val title: String,
    val message: String,
    val type: String,
    val priority: String,
    val templeId: String,
    val location: String?,
    val createdAt: String,
    val acknowledged: Boolean = false
)