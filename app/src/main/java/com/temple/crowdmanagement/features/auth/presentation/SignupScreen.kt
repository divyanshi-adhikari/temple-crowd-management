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
fun SignupScreen(
    onSignUpSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(context)
    )
    
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var showPass by remember { mutableStateOf(false) }
    var terms by remember { mutableStateOf(false) }
    
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val loggedIn by viewModel.isLoggedIn.collectAsState()

    LaunchedEffect(loggedIn) { if (loggedIn) onSignUpSuccess() }
    LaunchedEffect(error) { if (error != null) { kotlinx.coroutines.delay(3000); viewModel.clearError() } }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = onNavigateToLogin) { Icon(Icons.Default.ArrowBack, null) }
        }

        Spacer(Modifier.height(16.dp))
        Text("Create Account", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Join the community", fontSize = 14.sp, color = Color.Gray)

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = name, 
            onValueChange = { name = it }, 
            label = { Text("Name") },
            leadingIcon = { Icon(Icons.Default.Person, null) },
            modifier = Modifier.fillMaxWidth(), 
            shape = RoundedCornerShape(12.dp), 
            singleLine = true, 
            enabled = !loading
        )

        Spacer(Modifier.height(10.dp))
        
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

        Spacer(Modifier.height(10.dp))
        
        OutlinedTextField(
            value = phone, 
            onValueChange = { phone = it }, 
            label = { Text("Phone") },
            leadingIcon = { Icon(Icons.Default.Phone, null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth(), 
            shape = RoundedCornerShape(12.dp), 
            singleLine = true, 
            enabled = !loading
        )

        Spacer(Modifier.height(10.dp))
        
        OutlinedTextField(
            value = password, 
            onValueChange = { password = it }, 
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, null) },
            trailingIcon = { 
                IconButton(onClick = { showPass = !showPass }) { 
                    Icon(if (showPass) Icons.Default.VisibilityOff else Icons.Default.Visibility, null) 
                } 
            },
            visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(), 
            shape = RoundedCornerShape(12.dp), 
            singleLine = true, 
            enabled = !loading
        )

        Spacer(Modifier.height(10.dp))
        
        OutlinedTextField(
            value = confirm, 
            onValueChange = { confirm = it }, 
            label = { Text("Confirm Password") },
            leadingIcon = { Icon(Icons.Default.Lock, null) },
            visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(), 
            shape = RoundedCornerShape(12.dp), 
            singleLine = true, 
            enabled = !loading
        )

        Spacer(Modifier.height(8.dp))

        Row(
            Modifier.fillMaxWidth().clickable { terms = !terms }, 
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = terms, 
                onCheckedChange = { terms = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary
                )
            )
            Text("I agree to Terms & Conditions", fontSize = 14.sp, color = Color.Gray)
        }

        Spacer(Modifier.height(8.dp))

        error?.let {
            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.Red.copy(0.1f))) {
                Text(it, color = Color.Red, modifier = Modifier.padding(12.dp))
            }
            Spacer(Modifier.height(8.dp))
        }

        Button(
            onClick = { 
                // ============ FIXED: Using public method ============
                if (!terms) {
                    viewModel.showError("Please accept the Terms & Conditions")
                } else {
                    viewModel.signUp(name, email, phone, password, confirm)
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = !loading
        ) {
            if (loading) CircularProgressIndicator(Modifier.size(24.dp), color = Color.White)
            else Text("Create Account", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(16.dp))

        Row {
            Text("Already have an account? ", color = Color.Gray)
            Text("Sign In", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold,
                 modifier = Modifier.clickable { onNavigateToLogin() })
        }
    }
}