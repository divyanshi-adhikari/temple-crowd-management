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
import com.temple.crowdmanagement.features.guide.model.FAQ
import com.temple.crowdmanagement.ui.theme.*

@Composable
fun FAQSection(
    faqs: List<FAQ>
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
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SaffronPrimary.copy(alpha = 0.15f),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            Icons.Default.Help,
                            contentDescription = "FAQ",
                            tint = SaffronPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = "Frequently Asked Questions",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "${faqs.size} common questions answered",
                        fontSize = 13.sp,
                        color = TextSecondary
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                //  Better badge design
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SaffronPrimary.copy(alpha = 0.15f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.QuestionAnswer,
                            contentDescription = null,
                            tint = SaffronLight,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${faqs.size}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = SaffronLight
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // FAQ Items
            faqs.forEachIndexed { index, faq ->
                FAQItem(
                    question = faq.question,
                    answer = faq.answer
                )
                
                if (index != faqs.size - 1) {
                    Divider(
                        color = Color.Gray.copy(alpha = 0.1f),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun FAQItem(
    question: String,
    answer: String
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(vertical = 8.dp)
    ) {
        // Question
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = question,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
            Icon(
                if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = if (expanded) "Collapse" else "Expand",
                tint = SaffronPrimary,
                modifier = Modifier.size(24.dp)
            )
        }

        // Answer (expanded)
        if (expanded) {
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White.copy(alpha = 0.05f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = answer,
                    fontSize = 14.sp,
                    color = TextSecondary,
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}