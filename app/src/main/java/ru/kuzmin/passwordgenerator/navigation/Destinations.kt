package ru.kuzmin.passwordgenerator.navigation

object Destinations {
    const val GENERATE = "generate"
    const val PASSWORD = "password"
    const val LIST = "passwordList"
    const val DETAILS = "passwordEdit/{passwordId}"

    fun createDetailsRoute(passwordId: Int) = "passwordEdit/$passwordId"
}