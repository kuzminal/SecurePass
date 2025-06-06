package ru.kuzmin.passwordgenerator.ui.screens.materpass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.kuzmin.passwordgenerator.domain.repositories.MasterPasswordRepository

/**
 * ViewModel for the password generator application.
 */
class MasterPasswordViewModel(private val repository: MasterPasswordRepository) : ViewModel() {
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
