package com.temple.crowdmanagement.features.auth

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LanguageScreen(
    onLanguageSelected: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(context)
    )
    
    var selected by remember { mutableStateOf(viewModel.selectedLanguage.value) }
    val languages = listOf(
        "en" to "English",
        "hi" to "हिन्दी (Hindi)",
        "gu" to "ગુજરાતી (Gujarati)"
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = onBack) { 
                Icon(Icons.Default.ArrowBack, contentDescription = "Back") 
            }
        }

        Spacer(Modifier.height(40.dp))
        
        Text("🛕", fontSize = 60.sp)
        Text("Select Language", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Choose your preferred language", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

        Spacer(Modifier.height(32.dp))

        Card(
            Modifier.fillMaxWidth(), 
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(Modifier.padding(8.dp)) {
                languages.forEachIndexed { i, (code, name) ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable { selected = code }
                            .padding(horizontal = 12.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            name, 
                            fontSize = 16.sp,
                            color = if (selected == code) 
                                MaterialTheme.colorScheme.primary 
                            else 
                                MaterialTheme.colorScheme.onSurface
                        )
                        RadioButton(
                            selected = selected == code,
                            onClick = { selected = code },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                    if (i != languages.size - 1) {
                        Divider(Modifier.padding(horizontal = 8.dp))
                    }
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = { 
                viewModel.updateLanguage(selected)
                onLanguageSelected() 
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text("Continue", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}