package com.example.booktracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val authRepository = AuthRepository()

    // ✅ Correct way: Use `StateFlow` instead of `LiveData`
    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> get() = _isAuthenticated

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val success = authRepository.login(email, password)
            _isAuthenticated.value = success
        }
    }

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            val success = authRepository.signup(email, password)
            _isAuthenticated.value = success
        }
    }

    fun logout() {
        authRepository.logout()
        _isAuthenticated.value = false
    }
}
