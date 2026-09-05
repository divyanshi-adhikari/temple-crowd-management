package com.temple.crowdmanagement.features.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.temple.crowdmanagement.core.model.TempleSite
import com.temple.crowdmanagement.features.dashboard.presentation.model.DashboardUiState
import com.temple.crowdmanagement.features.dashboard.presentation.repository.DashboardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: DashboardRepository = DashboardRepository()
) : ViewModel() {
    
    private val _selectedTemple = MutableStateFlow(TempleSite.SOMNATH)
    val selectedTemple: StateFlow<TempleSite> = _selectedTemple.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    fun loadDashboardData(templeSite: TempleSite = _selectedTemple.value) {
        viewModelScope.launch {
            _isLoading.value = true
            _uiState.value = repository.getDashboardData(templeSite)
            _isLoading.value = false
        }
    }

    fun selectTemple(templeSite: TempleSite) {
        _selectedTemple.value = templeSite
        loadDashboardData(templeSite)
    }
}