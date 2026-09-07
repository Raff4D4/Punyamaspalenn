package com.example.activites_journal.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.activites_journal.data.AppDao
import com.example.activites_journal.data.UserEntity
import com.example.activites_journal.utils.SessionManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: UserEntity) : AuthState()
    data class Error(val message: String) : AuthState()
    object Unauthenticated : AuthState()
}

class AuthViewModel(
    private val appDao: AppDao,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        viewModelScope.launch {
            sessionManager.isLoggedIn.collect { loggedIn ->
                if (!loggedIn) {
                    _authState.value = AuthState.Unauthenticated
                }
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val user = appDao.loginUser(username, password)
            if (user != null) {
                sessionManager.saveSession(user.id.toInt(), user.username, user.email)
                _authState.value = AuthState.Success(user)
            } else {
                _authState.value = AuthState.Error("Invalid credentials")
            }
        }
    }

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val newUser = UserEntity(username = username, email = email, password = password)
            val id = appDao.registerUser(newUser)
            if (id > 0) {
                sessionManager.saveSession(id.toInt(), username, email)
                _authState.value = AuthState.Success(newUser.copy(id = id))
            } else {
                _authState.value = AuthState.Error("Registration failed")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.clearSession()
            _authState.value = AuthState.Unauthenticated
        }
    }
}
