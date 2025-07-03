package ru.kuzmin.passwordgenerator

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.kuzmin.passwordgenerator.data.repositories.AppDatabase
import ru.kuzmin.passwordgenerator.data.repositories.PasswordRepository
import ru.kuzmin.passwordgenerator.domain.repositories.AppPreferences
import ru.kuzmin.passwordgenerator.domain.repositories.MasterPasswordRepository
import ru.kuzmin.passwordgenerator.navigation.AppNavGraph
import ru.kuzmin.passwordgenerator.navigation.Destinations
import ru.kuzmin.passwordgenerator.ui.screens.materpass.MasterPasswordViewModel
import ru.kuzmin.passwordgenerator.ui.theme.PasswordGeneratorTheme

/**
 * Main activity of the password generator application.
 */
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getInstance(this)
        val repository = PasswordRepository(database.passwordDao())
        val appPreferences = AppPreferences(this)

        setContent {
            PasswordGeneratorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val passwordRepository = remember { MasterPasswordRepository(applicationContext) }
                    val viewModel: MasterPasswordViewModel = viewModel(
                        factory = object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return MasterPasswordViewModel(passwordRepository, appPreferences) as T
                            }
                        }
                    )

                    AppNavGraph(
                        viewModel = viewModel,
                        repository = repository,
                        appPreferences = appPreferences,
                        startDestination = Destinations.PASSWORD
                    )
                }
            }
        }
    }
}




