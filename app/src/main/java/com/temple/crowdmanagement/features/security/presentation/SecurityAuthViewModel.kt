package com.temple.crowdmanagement.features.security.presentation

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private val Context.dataStore by preferencesDataStore(name = "security_auth")

class SecurityAuthViewModel(private val context: Context) : ViewModel() {
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn
    
    private val _securityName = MutableStateFlow("")
    val securityName: StateFlow<String> = _securityName
    
    private val _templeId = MutableStateFlow("")
    val templeId: StateFlow<String> = _templeId
    
    private val _badgeId = MutableStateFlow("")
    val badgeId: StateFlow<String> = _badgeId

    init {
        viewModelScope.launch {
            context.dataStore.data.collect { prefs ->
                _isLoggedIn.value = prefs[booleanPreferencesKey("security_logged_in")] ?: false
                _securityName.value = prefs[stringPreferencesKey("security_name")] ?: ""
                _templeId.value = prefs[stringPreferencesKey("temple_id")] ?: ""
                _badgeId.value = prefs[stringPreferencesKey("badge_id")] ?: ""
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            // Validation
            if (email.isEmpty() || password.isEmpty()) {
                showError("Fill all fields")
                _isLoading.value = false
                return@launch
            }
            
            // TODO: Replace with actual API call
            // Simulate network delay
            kotlinx.coroutines.delay(500)
            
            // ✅ For demo: Only accept specific security credentials
            // In production, this will be a real API call
            if (email == "security@temple.com" && password == "password123") {
                context.dataStore.edit { prefs ->
                    prefs[stringPreferencesKey("security_name")] = "Security Officer 1"
                    prefs[stringPreferencesKey("security_email")] = email
                    prefs[stringPreferencesKey("temple_id")] = "somnath"
                    prefs[stringPreferencesKey("badge_id")] = "SEC001"
                    prefs[booleanPreferencesKey("security_logged_in")] = true
                }
                _isLoggedIn.value = true
                _securityName.value = "Security Officer 1"
                _templeId.value = "somnath"
                _badgeId.value = "SEC001"
                _isLoading.value = false
            } else {
                showError("Invalid security credentials. Please check email and password.")
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            context.dataStore.edit { it.clear() }
            _isLoggedIn.value = false
            _securityName.value = ""
            _templeId.value = ""
            _badgeId.value = ""
        }
    }

    // ✅ NEW: Clear session without logging out (for testing)
    fun clearSession() {
        viewModelScope.launch {
            context.dataStore.edit { prefs ->
                // Clear only security auth keys
                prefs.remove(booleanPreferencesKey("security_logged_in"))
                prefs.remove(stringPreferencesKey("security_name"))
                prefs.remove(stringPreferencesKey("security_email"))
                prefs.remove(stringPreferencesKey("temple_id"))
                prefs.remove(stringPreferencesKey("badge_id"))
            }
            _isLoggedIn.value = false
            _securityName.value = ""
            _templeId.value = ""
            _badgeId.value = ""
            _error.value = null
        }
    }

    fun showError(message: String) {
        _error.value = message
    }

    fun clearError() { 
        _error.value = null 
    }
}

// ViewModel Factory
class SecurityAuthViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SecurityAuthViewModel::class.java)) {
            return SecurityAuthViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}