package ru.kuzmin.passwordgenerator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * ViewModel for the password generator application.
 */
class PasswordViewModel(private val repository: PasswordRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<PasswordUiState>(PasswordUiState.Loading)
    val uiState: StateFlow<PasswordUiState> = _uiState

    init {
        checkPassword()
    }

    private fun checkPassword() {
        viewModelScope.launch {
            repository.hasPassword.collect { hasPassword ->
                _uiState.value = if (hasPassword) {
                    PasswordUiState.PasswordExists
                } else {
                    PasswordUiState.NoPassword
                }
            }
        }
    }

    fun savePassword(password: String) {
        viewModelScope.launch {
            repository.savePassword(password)
            _uiState.value = PasswordUiState.PasswordExists
        }
    }

    fun validatePassword(input: String): Flow<Boolean> {
        return repository.getPassword()
            .map { savedPassword -> savedPassword == input }
    }
}

sealed class PasswordUiState {
    object Loading : PasswordUiState()
    object NoPassword : PasswordUiState()
    object PasswordExists : PasswordUiState()
}
