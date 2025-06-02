package ru.kuzmin.passwordgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.kuzmin.passwordgenerator.ui.theme.PasswordGeneratorTheme

/**
 * Main activity of the password generator application.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordGeneratorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val passwordRepository = remember { PasswordRepository(applicationContext) }
                    val viewModel: PasswordViewModel = viewModel(
                        factory = object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return PasswordViewModel(passwordRepository) as T
                            }
                        }
                    )

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "password"
                    ) {
                        composable("password") {
                            PasswordScreen(
                                viewModel = viewModel,
                                onPasswordVerified = {
                                    navController.navigate("main") {
                                        popUpTo("password") { inclusive = true }
                                    }
                                },
                                onNewPasswordCreated = {
                                    navController.navigate("main") {
                                        popUpTo("password") { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable("main") {
                            PasswordGeneratorScreen()
                        }
                    }
                }
            }
        }
    }
}




