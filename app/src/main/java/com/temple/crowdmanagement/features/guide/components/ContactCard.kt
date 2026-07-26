package com.temple.crowdmanagement.features.guide.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.temple.crowdmanagement.features.guide.model.Contact
import com.temple.crowdmanagement.ui.theme.*

@Composable
fun ContactCard(
    contacts: List<Contact>
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.ContactPhone,
                    contentDescription = "Contacts",
                    tint = SaffronPrimary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Contact Us",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Contact Grid
            contacts.forEachIndexed { index, contact ->
                ContactItem(
                    type = contact.type,
                    value = contact.value,
                    icon = contact.icon
                )
                
                if (index != contacts.size - 1) {
                    Divider(
                        color = Color.Gray.copy(alpha = 0.1f),
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ContactItem(
    type: String,
    value: String,
    icon: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Copy to clipboard or call */ }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        Surface(
            shape = RoundedCornerShape(10.dp),
            color = SaffronPrimary.copy(alpha = 0.15f),
            modifier = Modifier.size(44.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = icon,
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        // Contact Info
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = type,
                fontSize = 13.sp,
                color = TextSecondary
            )
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }

        // Action Icon
        Icon(
            when (type) {
                "Email" -> Icons.Default.Email
                "Website" -> Icons.Default.OpenInNew
                else -> Icons.Default.Call
            },
            contentDescription = "Action",
            tint = SaffronPrimary,
            modifier = Modifier.size(20.dp)
        )
    }
}