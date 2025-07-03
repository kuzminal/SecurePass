package ru.kuzmin.passwordgenerator.security

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity

class BiometricAuthHelper(
    private val context: Context,
    private val onSuccess: () -> Unit,
    private val onError: (String) -> Unit
) {
    private val executor = context.mainExecutor
    private lateinit var biometricPrompt: BiometricPrompt

    fun checkBiometricAvailable(): Boolean {
        val biometricManager = BiometricManager.from(context)
        return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> {
                onError(getErrorMessage(biometricManager))
                false
            }
        }
    }

    private fun getErrorMessage(biometricManager: BiometricManager): String {
        return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> "Биометрический датчик не доступен"
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> "Биометрический датчик временно недоступен"
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> "Не зарегистрировано ни одного биометрического идентификатора"
            else -> "Биометрическая аутентификация недоступна"
        }
    }

    fun showBiometricPrompt(activity: FragmentActivity) {
        if (!checkBiometricAvailable()) return

        biometricPrompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onError("Аутентификация не удалась")
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    onError(errString.toString())
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Биометрическая аутентификация")
            .setSubtitle("Используйте отпечаток пальца или Face ID для входа")
            .setNegativeButtonText("Использовать пароль")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}