package com.temple.crowdmanagement.features.guide.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.temple.crowdmanagement.core.model.TempleSite
import com.temple.crowdmanagement.features.guide.model.GuideData
import com.temple.crowdmanagement.features.guide.repository.GuideRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GuideViewModel(
    private val repository: GuideRepository = GuideRepository()
) : ViewModel() {
    
    private val _selectedTemple = MutableStateFlow(TempleSite.SOMNATH)
    val selectedTemple: StateFlow<TempleSite> = _selectedTemple.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _guideData = MutableStateFlow(GuideData())
    val guideData: StateFlow<GuideData> = _guideData.asStateFlow()
    
    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    fun loadGuideData(templeSite: TempleSite = _selectedTemple.value) {
        viewModelScope.launch {
            _isLoading.value = true
            _guideData.value = repository.getGuideData(templeSite)
            _isLoading.value = false
        }
    }

    fun selectTemple(templeSite: TempleSite) {
        _selectedTemple.value = templeSite
        loadGuideData(templeSite)
    }
    
    fun selectTab(index: Int) {
        _selectedTab.value = index
    }
}