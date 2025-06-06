package ru.kuzmin.passwordgenerator.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.kuzmin.passwordgenerator.data.local.dao.PasswordDao
import ru.kuzmin.passwordgenerator.data.local.entities.PasswordEntity
import ru.kuzmin.passwordgenerator.security.SecurityUtils

class PasswordRepository(private val passwordDao: PasswordDao) {
    val allPasswords: Flow<List<PasswordEntity>> = passwordDao.getAllPasswords().map { list ->
        list.map { it.copy(encryptedPassword = SecurityUtils.decrypt(it.encryptedPassword)) }}

    suspend fun insert(password: PasswordEntity) {
        val encrypted = password.copy(
            encryptedPassword = SecurityUtils.encrypt(password.encryptedPassword)
        )
        passwordDao.insert(encrypted)
    }

    suspend fun update(password: PasswordEntity) {
        passwordDao.update(password)
    }

    suspend fun delete(password: PasswordEntity) {
        passwordDao.delete(password)
    }

    suspend fun getPasswordById(id: Int): PasswordEntity? {
        return passwordDao.getPasswordById(id).let { it?.copy(encryptedPassword = SecurityUtils.decrypt(it.encryptedPassword)) }
    }
}