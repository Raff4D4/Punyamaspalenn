package com.example.activites_journal.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "session_prefs")

class SessionManager(private val context: Context) {
    companion object {
        private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val USER_ID = intPreferencesKey("user_id")
        private val USERNAME = stringPreferencesKey("username")
        private val EMAIL = stringPreferencesKey("email")
        private val THEME_MODE = stringPreferencesKey("theme_mode")
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { it[IS_LOGGED_IN] ?: false }
    val userId: Flow<Int?> = context.dataStore.data.map { it[USER_ID] }
    val username: Flow<String?> = context.dataStore.data.map { it[USERNAME] }
    val email: Flow<String?> = context.dataStore.data.map { it[EMAIL] }
    val themeMode: Flow<String> = context.dataStore.data.map { it[THEME_MODE] ?: "System" }

    suspend fun saveThemeMode(mode: String) {
        context.dataStore.edit {
            it[THEME_MODE] = mode
        }
    }

    suspend fun saveSession(id: Int, username: String, email: String) {
        context.dataStore.edit {
            it[IS_LOGGED_IN] = true
            it[USER_ID] = id
            it[USERNAME] = username
            it[EMAIL] = email
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit {
            it.clear()
        }
    }
}
