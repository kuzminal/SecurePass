package ru.kuzmin.passwordgenerator.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import ru.kuzmin.passwordgenerator.ui.screens.PasswordGeneratorScreen
import ru.kuzmin.passwordgenerator.ui.screens.PasswordScreen
import ru.kuzmin.passwordgenerator.ui.screens.PasswordViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.PASSWORD,
    viewModel: PasswordViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Destinations.PASSWORD) {
            PasswordScreen(
                viewModel = viewModel,
                onPasswordVerified = {
                    navController.navigate(Destinations.MAIN) {
                        popUpTo(Destinations.PASSWORD) { inclusive = true }
                    }
                },
                onNewPasswordCreated = {
                    navController.navigate(Destinations.MAIN) {
                        popUpTo(Destinations.PASSWORD) { inclusive = true }
                    }
                }
            )
        }

        composable(Destinations.MAIN) {
            PasswordGeneratorScreen()
        }
    }
}