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
import com.temple.crowdmanagement.features.security.presentation.SecurityAuthViewModel
import com.temple.crowdmanagement.features.security.presentation.SecurityAuthViewModelFactory
import com.temple.crowdmanagement.ui.theme.*

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSecurityLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToLanguage: () -> Unit
) {
    val context = LocalContext.current
    
    val pilgrimViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(context)
    )
    
    val securityViewModel: SecurityAuthViewModel = viewModel(
        factory = SecurityAuthViewModelFactory(context)
    )
    
    // ✅ Keep this as safety
    LaunchedEffect(Unit) {
        pilgrimViewModel.clearSession()
        securityViewModel.clearSession()
    }
    
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf("pilgrim") }
    
    val pilgrimLoading by pilgrimViewModel.isLoading.collectAsState()
    val pilgrimError by pilgrimViewModel.error.collectAsState()
    val pilgrimLoggedIn by pilgrimViewModel.isLoggedIn.collectAsState()
    
    val securityLoading by securityViewModel.isLoading.collectAsState()
    val securityError by securityViewModel.error.collectAsState()
    val securityLoggedIn by securityViewModel.isLoggedIn.collectAsState()
    
    val isLoading = if (selectedRole == "security") securityLoading else pilgrimLoading
    val error = if (selectedRole == "security") securityError else pilgrimError

    LaunchedEffect(pilgrimLoggedIn) { 
        if (pilgrimLoggedIn) onLoginSuccess() 
    }
    LaunchedEffect(securityLoggedIn) { 
        if (securityLoggedIn) onSecurityLoginSuccess() 
    }
    
    LaunchedEffect(error) { 
        if (error != null) { 
            kotlinx.coroutines.delay(3000)
            if (selectedRole == "security") {
                securityViewModel.clearError()
            } else {
                pilgrimViewModel.clearError()
            }
        } 
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = onNavigateToLanguage) {
                Icon(Icons.Default.Language, null)
                Spacer(Modifier.width(4.dp))
                Text("Language")
            }
        }

        Spacer(Modifier.weight(0.2f))
        
        Text("🛕", fontSize = 60.sp)
        Text("Welcome!", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Sign in to continue", fontSize = 14.sp, color = TextSecondary)

        Spacer(Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select Your Role",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                RoleButton(
                    modifier = Modifier.weight(1f),
                    icon = "🙏",
                    label = "Pilgrim",
                    isSelected = selectedRole == "pilgrim",
                    onClick = { selectedRole = "pilgrim" }
                )
                
                RoleButton(
                    modifier = Modifier.weight(1f),
                    icon = "🛡️",
                    label = "Security",
                    isSelected = selectedRole == "security",
                    onClick = { selectedRole = "security" }
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            enabled = !isLoading,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SaffronPrimary,
                unfocusedBorderColor = TextSecondary.copy(alpha = 0.3f)
            )
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
            enabled = !isLoading,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SaffronPrimary,
                unfocusedBorderColor = TextSecondary.copy(alpha = 0.3f)
            )
        )

        Spacer(Modifier.height(8.dp))
        Text(
            text = "Forgot Password?",
            color = SaffronPrimary,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { }
                .padding(end = 8.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.End
        )

        Spacer(Modifier.height(16.dp))

        error?.let {
            Card(
                Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = StatusRed.copy(alpha = 0.1f)
                )
            ) {
                Text(
                    it,
                    color = StatusRed,
                    modifier = Modifier.padding(12.dp)
                )
            }
            Spacer(Modifier.height(8.dp))
        }

        Button(
            onClick = { 
                if (selectedRole == "security") {
                    securityViewModel.login(email, password)
                } else {
                    pilgrimViewModel.login(email, password)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedRole == "security") 
                    SaffronPrimary.copy(alpha = 0.8f) 
                else 
                    SaffronPrimary
            )
        ) {
            if (isLoading) {
                CircularProgressIndicator(Modifier.size(24.dp), color = Color.White)
            } else {
                Text(
                    text = if (selectedRole == "security") "Sign in as Security" else "Sign In",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        if (selectedRole == "pilgrim") {
            Row {
                Text("Don't have an account? ", color = TextSecondary)
                Text(
                    "Sign Up",
                    color = SaffronPrimary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onNavigateToSignUp() }
                )
            }
        } else {
            Text(
                text = "Security personnel accounts are managed by the temple administration",
                fontSize = 12.sp,
                color = TextSecondary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        
        Spacer(Modifier.weight(0.2f))
    }
}

@Composable
fun RoleButton(
    modifier: Modifier = Modifier,
    icon: String,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) SaffronPrimary.copy(alpha = 0.15f) 
                else Color.Black.copy(alpha = 0.05f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = icon, fontSize = 24.sp)
            Spacer(Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) SaffronPrimary else TextPrimary
            )
            if (isSelected) {
                Surface(
                    modifier = Modifier.size(6.dp),
                    shape = RoundedCornerShape(50),
                    color = SaffronPrimary
                ) { }
            }
        }
    }
}