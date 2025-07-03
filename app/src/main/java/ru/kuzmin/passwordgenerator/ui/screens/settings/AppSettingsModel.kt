package ru.kuzmin.passwordgenerator.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.kuzmin.passwordgenerator.domain.repositories.AppPreferences

class AppSettingsModel( private val appPreferences: AppPreferences) : ViewModel() {
    fun isBiometricEnabled(): Boolean {
        return appPreferences.isBiometricEnabled
    }

    fun setBiometricAuth(enabled: Boolean) {
        viewModelScope.launch {
            appPreferences.isBiometricEnabled = enabled
        }
    }
}