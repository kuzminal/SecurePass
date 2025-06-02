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
class PasswordGeneratorViewModel() : ViewModel() {
    // Состояние приложения
    /**
     * The generated password.
     */
    var password by mutableStateOf("")

    /**
     * The length of the generated password.
     */
    var passwordLength by mutableIntStateOf(12)

    /**
     * Whether to include uppercase letters in the generated password.
     */
    var includeUppercase by mutableStateOf(true)

    /**
     * Whether to include numbers in the generated password.
     */
    var includeNumbers by mutableStateOf(true)

    /**
     * Whether to include special characters in the generated password.
     */
    var includeSpecial by mutableStateOf(true)

    /**
     * Whether the generated password has been copied to the clipboard.
     */
    var isCopied by mutableStateOf(false)

    // Символы для генерации
    /**
     * The list of lowercase letters.
     */
    private val lowercaseChars = ('a'..'z').toList()

    /**
     * The list of uppercase letters.
     */
    private val uppercaseChars = ('A'..'Z').toList()

    /**
     * The list of numbers.
     */
    private val numberChars = ('0'..'9').toList()

    /**
     * The list of special characters.
     */
    private val specialChars = listOf('!', '@', '#', '$', '%', '^', '&', '*', '(', ')')

    // Генерация пароля
    /**
     * Generates a new password based on the current settings.
     */
    fun generatePassword() {
        val allowedChars = mutableListOf<Char>().apply {
            addAll(lowercaseChars)
            if (includeUppercase) addAll(uppercaseChars)
            if (includeNumbers) addAll(numberChars)
            if (includeSpecial) addAll(specialChars)
        }

        password = buildString {
            repeat(passwordLength) {
                append(allowedChars.random())
            }
        }
        isCopied = false
    }

    // Копирование в буфер обмена
    /**
     * Copies the generated password to the clipboard.
     *
     * @param context The context of the application.
     */
    fun copyToClipboard(context: android.content.Context) {
        val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE)
                as android.content.ClipboardManager
        val clip = android.content.ClipData.newPlainText("Generated Password", password)
        clipboard.setPrimaryClip(clip)
        isCopied = true
    }
}