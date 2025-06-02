package ru.kuzmin.passwordgenerator

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "password_store")

class PasswordRepository(private val context: Context) {
    private val PASSWORD_KEY = stringPreferencesKey("app_password")

    suspend fun savePassword(password: String) {
        context.dataStore.edit { preferences ->
            preferences[PASSWORD_KEY] = password
        }
    }

    suspend fun clearPassword() {
        context.dataStore.edit { preferences ->
            preferences.remove(PASSWORD_KEY)
        }
    }

    val hasPassword: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[PASSWORD_KEY] != null
        }

    fun getPassword(): Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PASSWORD_KEY]
        }
}