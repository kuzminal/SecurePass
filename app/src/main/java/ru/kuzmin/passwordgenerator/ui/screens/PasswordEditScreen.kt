package ru.kuzmin.passwordgenerator.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.kuzmin.passwordgenerator.data.local.entities.PasswordEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordEditScreen(
    passwordId: Int,
    viewModel: PasswordViewModel = viewModel(),
    onBack: () -> Unit,
    onNewPasswordGenerate: () -> Unit
) {
    val password = viewModel.getPasswordById(passwordId)
    var title by rememberSaveable { mutableStateOf(password?.title ?: "") }
    var username by rememberSaveable { mutableStateOf(password?.username ?: "") }
    var passwordText by rememberSaveable { mutableStateOf(password?.encryptedPassword ?: "") }
    var website by rememberSaveable { mutableStateOf(password?.website ?: "") }
    var notes by rememberSaveable { mutableStateOf(password?.notes ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (password == null) "Новый пароль" else "Редактировать") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Назад")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it.toString()
                },
                label = { Text("Название") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            OutlinedTextField(
                value = username,
                onValueChange = { username = it.toString() },
                label = { Text("Имя пользователя") },
                modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            )

            var passwordVisible by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = passwordText,
                onValueChange = { passwordText = it.toString() },
                label = { Text("Пароль") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible) "Скрыть пароль" else "Показать пароль"
                        )
                    }
                }
            )

            Button(
                onClick = { onNewPasswordGenerate() },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Сгенерировать пароль")
            }

//            OutlinedTextField(
//                value = passwordText,
//                onValueChange = { passwordText = it.toString() },
//                label = { Text("Пароль") },
//                modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp)
//            )

            OutlinedTextField(
                value = website,
                onValueChange = { website = it.toString() },
                label = { Text("Сайт") },
                modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            )

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it.toString() },
                label = { Text("Заметки") },
                modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            )

            Button(
                onClick = {
                    val newPassword = PasswordEntity(
                        id = password?.id ?: 0,
                        title = title,
                        username = username,
                        encryptedPassword = passwordText,
                        website = website,
                        notes = notes
                    )

                    if (password == null) {
                        viewModel.addPassword(newPassword)
                    } else {
                        viewModel.updatePassword(newPassword)
                    }
                    onBack()
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Сохранить")
            }
        }
    }
}