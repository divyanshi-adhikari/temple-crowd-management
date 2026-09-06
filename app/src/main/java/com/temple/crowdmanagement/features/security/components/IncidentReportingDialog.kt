package com.temple.crowdmanagement.features.security.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.temple.crowdmanagement.features.security.model.IncidentType
import com.temple.crowdmanagement.features.security.model.IncidentPriority
import com.temple.crowdmanagement.features.security.model.IncidentPriority.*
import androidx.compose.foundation.clickable
import com.temple.crowdmanagement.ui.theme.*

@Composable
fun IncidentReportingDialog(
    onDismiss: () -> Unit,
    onSubmit: (
        type: IncidentType,
        priority: IncidentPriority,
        title: String,
        description: String,
        location: String
    ) -> Unit
) {
    var selectedType by remember { mutableStateOf<IncidentType?>(null) }
    var selectedPriority by remember { mutableStateOf<IncidentPriority?>(null) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var showTypeDropdown by remember { mutableStateOf(false) }
    var showPriorityDropdown by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .heightIn(max = 600.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = SpiritualDarkBg
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "🚨 Report Incident",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = onDismiss) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Fill in the details below",
                    fontSize = 13.sp,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Incident Type
                Text(
                    text = "Incident Type *",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = selectedType?.displayName ?: "",
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Select Type", color = TextSecondary) },
                    trailingIcon = {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown",
                            tint = SaffronPrimary
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showTypeDropdown = !showTypeDropdown },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SaffronPrimary,
                        unfocusedBorderColor = TextSecondary.copy(alpha = 0.3f)
                    )
                )

                // Type Dropdown
                if (showTypeDropdown) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = CardDarkBg
                        )
                    ) {
                        Column {
                            IncidentType.values().forEach { type ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedType = type
                                            showTypeDropdown = false
                                        }
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = type.icon,
                                        fontSize = 16.sp
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = type.displayName,
                                        fontSize = 14.sp,
                                        color = if (selectedType == type) SaffronPrimary else Color.Black
                                    )
                                    if (selectedType == type) {
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text(
                                            text = "✓",
                                            color = SaffronPrimary,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Priority
                Text(
                    text = "Priority *",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = selectedPriority?.displayName ?: "",
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Select Priority", color = TextSecondary) },
                    trailingIcon = {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown",
                            tint = SaffronPrimary
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showPriorityDropdown = !showPriorityDropdown },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SaffronPrimary,
                        unfocusedBorderColor = TextSecondary.copy(alpha = 0.3f)
                    )
                )

                if (showPriorityDropdown) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = CardDarkBg
                        )
                    ) {
                        Column {
                            IncidentPriority.values().forEach { priority ->
                                // ✅ Fixed: Use Surface instead of Box with background
                                val priorityColor = when (priority) {
                                    LOW -> Color(0xFF8BC34A)
                                    MEDIUM -> Color(0xFFFFC107)
                                    HIGH -> Color(0xFFFF9800)
                                    CRITICAL -> Color(0xFFF44336)
                                    URGENT -> TODO()
                                }
                                
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selectedPriority = priority
                                            showPriorityDropdown = false
                                        }
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // ✅ Use Surface instead of Box with background
                                    Surface(
                                        modifier = Modifier.size(12.dp),
                                        shape = RoundedCornerShape(50),
                                        color = priorityColor
                                    ) { }
                                    
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = priority.displayName,
                                        fontSize = 14.sp,
                                        color = if (selectedPriority == priority) SaffronPrimary else Color.Black
                                    )
                                    if (selectedPriority == priority) {
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text(
                                            text = "✓",
                                            color = SaffronPrimary,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Title
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title *", color = TextSecondary) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SaffronPrimary,
                        unfocusedBorderColor = TextSecondary.copy(alpha = 0.3f)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Location
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location *", color = TextSecondary) },
                    leadingIcon = {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = TextSecondary
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SaffronPrimary,
                        unfocusedBorderColor = TextSecondary.copy(alpha = 0.3f)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Description
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description", color = TextSecondary) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = SaffronPrimary,
                        unfocusedBorderColor = TextSecondary.copy(alpha = 0.3f)
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Submit Button
                Button(
                    onClick = {
                        selectedType?.let { type ->
                            selectedPriority?.let { priority ->
                                if (title.isNotEmpty() && location.isNotEmpty()) {
                                    onSubmit(type, priority, title, description, location)
                                    onDismiss()
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    enabled = selectedType != null && 
                              selectedPriority != null && 
                              title.isNotEmpty() && 
                              location.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SaffronPrimary
                    )
                ) {
                    Text(
                        text = "Submit Incident",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }
    }
}