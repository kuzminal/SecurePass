package ru.kuzmin.passwordgenerator.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.kuzmin.passwordgenerator.data.repositories.PasswordRepository
import ru.kuzmin.passwordgenerator.navigation.Destinations.createDetailsRoute
import ru.kuzmin.passwordgenerator.ui.screens.PasswordEditScreen
import ru.kuzmin.passwordgenerator.ui.screens.PasswordListScreen
import ru.kuzmin.passwordgenerator.ui.screens.PasswordViewModel
import ru.kuzmin.passwordgenerator.ui.screens.generate.PasswordGeneratorScreen
import ru.kuzmin.passwordgenerator.ui.screens.materpass.PasswordScreen
import ru.kuzmin.passwordgenerator.ui.screens.materpass.MasterPasswordViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.PASSWORD,
    viewModel: MasterPasswordViewModel = viewModel(),
    repository: PasswordRepository
) {
    val passwordViewmodel: PasswordViewModel = viewModel(factory = PasswordViewModelFactory(repository))
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Destinations.PASSWORD) {
            PasswordScreen(
                viewModel = viewModel,
                onPasswordVerified = {
                    navController.navigate(Destinations.LIST) {
                        popUpTo(Destinations.PASSWORD) { inclusive = true }
                    }
                },
                onNewPasswordCreated = {
                    navController.navigate(Destinations.LIST) {
                        popUpTo(Destinations.PASSWORD) { inclusive = true }
                    }
                }
            )
        }

        composable(Destinations.LIST) {
            PasswordListScreen(
                viewModel = passwordViewmodel,
                onItemClick = { password ->
                    navController.navigate(createDetailsRoute(password.id))
                },
                onAddClick = {
                    navController.navigate(createDetailsRoute(0))
                }
            )
        }

        composable(
            Destinations.DETAILS,
            arguments = listOf(navArgument(
                NavArguments.PASSWORD_ID) { type = NavType.IntType })
            ) { backStackEntry ->
                val passwordId = backStackEntry.arguments?.getInt("passwordId") ?: 0
                PasswordEditScreen(
                    passwordId = passwordId,
                    viewModel = passwordViewmodel,
                    onBack = { navController.popBackStack() },
                    onNewPasswordGenerate = { navController.navigate(Destinations.GENERATE) }
                )
            }

        composable(Destinations.GENERATE) {
            PasswordGeneratorScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}

class PasswordViewModelFactory(private val repository: PasswordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PasswordViewModel(repository) as T
    }
}