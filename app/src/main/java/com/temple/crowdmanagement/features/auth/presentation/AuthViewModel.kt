package com.temple.crowdmanagement.features.auth

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private val Context.dataStore by preferencesDataStore(name = "auth")

class AuthViewModel(private val context: Context) : ViewModel() {
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn
    
    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName
    
    private val _selectedLang = MutableStateFlow("en")
    val selectedLanguage: StateFlow<String> = _selectedLang

    init {
        viewModelScope.launch {
            context.dataStore.data.collect { prefs ->
                _isLoggedIn.value = prefs[booleanPreferencesKey("logged_in")] ?: false
                _userName.value = prefs[stringPreferencesKey("name")] ?: ""
                _selectedLang.value = prefs[stringPreferencesKey("lang")] ?: "en"
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            if (email.isEmpty() || password.isEmpty()) {
                showError("Fill all fields")
                _isLoading.value = false
                return@launch
            }
            
            kotlinx.coroutines.delay(500)
            
            context.dataStore.edit { prefs ->
                prefs[stringPreferencesKey("name")] = "Devotee"
                prefs[stringPreferencesKey("email")] = email
                prefs[booleanPreferencesKey("logged_in")] = true
            }
            _isLoggedIn.value = true
            _userName.value = "Devotee"
            _isLoading.value = false
        }
    }

    fun signUp(name: String, email: String, phone: String, password: String, confirm: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                showError("All fields required")
                _isLoading.value = false
                return@launch
            }
            if (password != confirm) {
                showError("Passwords don't match")
                _isLoading.value = false
                return@launch
            }
            if (password.length < 6) {
                showError("Password too short")
                _isLoading.value = false
                return@launch
            }
            
            kotlinx.coroutines.delay(500)
            
            context.dataStore.edit { prefs ->
                prefs[stringPreferencesKey("name")] = name
                prefs[stringPreferencesKey("email")] = email
                prefs[stringPreferencesKey("phone")] = phone
                prefs[booleanPreferencesKey("logged_in")] = true
            }
            _isLoggedIn.value = true
            _userName.value = name
            _isLoading.value = false
        }
    }

    fun logout() {
        viewModelScope.launch {
            context.dataStore.edit { it.clear() }
            _isLoggedIn.value = false
            _userName.value = ""
        }
    }

    fun updateLanguage(lang: String) {
        viewModelScope.launch {
            context.dataStore.edit { prefs ->
                prefs[stringPreferencesKey("lang")] = lang
            }
            _selectedLang.value = lang
        }
    }

    // ============ PUBLIC METHOD FOR SHOWING ERRORS ============
    fun showError(message: String) {
        _error.value = message
    }

    fun clearError() { 
        _error.value = null 
    }
}

// ViewModel Factory
class AuthViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}