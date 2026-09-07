package com.example.activites_journal.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activites_journal.utils.SessionManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val sessionManager: SessionManager) : ViewModel() {
    val themeMode: StateFlow<String> = sessionManager.themeMode
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "System")

    val username: StateFlow<String?> = sessionManager.username
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val email: StateFlow<String?> = sessionManager.email
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val isLoggedIn: StateFlow<Boolean> = sessionManager.isLoggedIn
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    fun setThemeMode(mode: String) {
        viewModelScope.launch {
            sessionManager.saveThemeMode(mode)
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clearSession()
        }
    }
}
