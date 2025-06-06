package ru.kuzmin.passwordgenerator.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.kuzmin.passwordgenerator.data.local.entities.PasswordEntity
import ru.kuzmin.passwordgenerator.data.repositories.PasswordRepository

class PasswordViewModel(private val repository: PasswordRepository) : ViewModel() {
    val passwords = repository.allPasswords

    fun addPassword(password: PasswordEntity) = viewModelScope.launch {
        repository.insert(password)
    }

    fun updatePassword(password: PasswordEntity) = viewModelScope.launch {
        repository.update(password)
    }

    fun deletePassword(password: PasswordEntity) = viewModelScope.launch {
        repository.delete(password)
    }

    fun getPasswordById(id: Int): PasswordEntity? {
        return runBlocking { repository.getPasswordById(id) }
    }
}