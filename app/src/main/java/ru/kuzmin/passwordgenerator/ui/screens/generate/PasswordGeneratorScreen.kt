package ru.kuzmin.passwordgenerator.ui.screens.generate

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Composable function that represents the main screen of the password generator.
 *
 * @param viewModel The view model for the password generator.
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PasswordGeneratorScreen(
    viewModel: PasswordGeneratorViewModel = viewModel(),
    onBack: () -> Unit
) {
    val context = LocalContext.current
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text( "Генератор паролей" ) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Заголовок
//            Text(
//                text = "Генератор паролей",
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(bottom = 24.dp)
//            )

            // Сгенерированный пароль
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = viewModel.password.ifEmpty { "Нажмите 'Сгенерировать'" },
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    Button(
                        onClick = { viewModel.copyToClipboard(context) },
                        enabled = viewModel.password.isNotEmpty()
                    ) {
                        Text(if (viewModel.isCopied) "Скопировано!" else "Копировать")
                    }
                }
            }

            // Настройки генерации
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Настройки",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Длина пароля
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = "Длина: ${viewModel.passwordLength}",
                            modifier = Modifier.weight(1f)
                        )
                        Slider(
                            value = viewModel.passwordLength.toFloat(),
                            onValueChange = { viewModel.passwordLength = it.toInt() },
                            valueRange = 4f..32f,
                            steps = 28,
                            modifier = Modifier.weight(2f)
                        )
                    }

                    // Опции символов
                    CheckboxOption(
                        text = "Заглавные буквы (A-Z)",
                        checked = viewModel.includeUppercase,
                        onCheckedChange = { viewModel.includeUppercase = it }
                    )

                    CheckboxOption(
                        text = "Цифры (0-9)",
                        checked = viewModel.includeNumbers,
                        onCheckedChange = { viewModel.includeNumbers = it }
                    )

                    CheckboxOption(
                        text = "Спецсимволы (!@#...)",
                        checked = viewModel.includeSpecial,
                        onCheckedChange = { viewModel.includeSpecial = it }
                    )
                }
            }

            // Кнопка генерации
            Button(
                onClick = { viewModel.generatePassword() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text("Сгенерировать пароль", fontSize = 18.sp)
            }
        }
    }
}

/**
 * Composable function that represents a checkbox option.
 *
 * @param text The text of the checkbox option.
 * @param checked Whether the checkbox is checked.
 * @param onCheckedChange The callback to be invoked when the checkbox is checked or unchecked.
 */
@Composable
fun CheckboxOption(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
