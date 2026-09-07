package com.example.activites_journal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.activites_journal.data.AppDao
import com.example.activites_journal.utils.SessionManager
import com.example.activites_journal.viewmodels.AuthViewModel
import com.example.activites_journal.viewmodels.HabitViewModel
import com.example.activites_journal.viewmodels.JournalViewModel
import com.example.activites_journal.viewmodels.SettingsViewModel

class ViewModelFactory(
    private val appDao: AppDao,
    private val sessionManager: SessionManager
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) ->
                AuthViewModel(appDao, sessionManager) as T
            modelClass.isAssignableFrom(HabitViewModel::class.java) ->
                HabitViewModel(appDao, sessionManager) as T
            modelClass.isAssignableFrom(JournalViewModel::class.java) ->
                JournalViewModel(appDao, sessionManager) as T
            modelClass.isAssignableFrom(SettingsViewModel::class.java) ->
                SettingsViewModel(sessionManager) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
