package com.temple.crowdmanagement.features.dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.temple.crowdmanagement.ui.theme.*  

@Composable
fun HeaderSection(
    name: String,
    isOpen: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = ElevatedSurface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "☀ Good Morning, $name",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = SaffronPrimary  
            )
            Text(
                text = "Today is a blessed day at Somnath Temple",
                fontSize = 14.sp,
                color = TextSecondary,  
                modifier = Modifier.padding(top = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            color = if (isOpen) SuccessGreen else DangerRed,  
                            shape = RoundedCornerShape(50)
                        )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isOpen) "🔔 Temple Status : OPEN" else "🔔 Temple Status : CLOSED",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isOpen) SuccessGreen else DangerRed  
                )
            }
        }
    }
}