package com.temple.crowdmanagement.features.auth

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToLanguage: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(context)
    )
    
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val loggedIn by viewModel.isLoggedIn.collectAsState()

    LaunchedEffect(loggedIn) { if (loggedIn) onLoginSuccess() }
    LaunchedEffect(error) { if (error != null) { kotlinx.coroutines.delay(3000); viewModel.clearError() } }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            TextButton(onClick = onNavigateToLanguage) {
                Icon(Icons.Default.Language, null)
                Spacer(Modifier.width(4.dp))
                Text("Language")
            }
        }

        Spacer(Modifier.weight(0.3f))
        
        Text("🛕", fontSize = 60.sp)
        Text("Welcome!", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Sign in to continue", fontSize = 14.sp, color = Color.Gray)

        Spacer(Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            enabled = !loading
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, null) },
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility, null)
                }
            },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            enabled = !loading
        )

        Spacer(Modifier.height(8.dp))
        Text("Forgot Password?", color = MaterialTheme.colorScheme.primary, 
             modifier = Modifier.fillMaxWidth().clickable { }.padding(end = 8.dp),
             textAlign = androidx.compose.ui.text.style.TextAlign.End)

        Spacer(Modifier.height(16.dp))

        error?.let {
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.Red.copy(0.1f))) {
                Text(it, color = Color.Red, modifier = Modifier.padding(12.dp))
            }
            Spacer(Modifier.height(8.dp))
        }

        Button(
            onClick = { viewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = !loading
        ) {
            if (loading) CircularProgressIndicator(Modifier.size(24.dp), color = Color.White)
            else Text("Sign In", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(16.dp))

        Row {
            Text("Don't have an account? ", color = Color.Gray)
            Text("Sign Up", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold,
                 modifier = Modifier.clickable { onNavigateToSignUp() })
        }
        
        Spacer(Modifier.weight(0.3f))
    }
}