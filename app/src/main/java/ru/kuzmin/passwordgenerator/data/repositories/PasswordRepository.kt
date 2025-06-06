package ru.kuzmin.passwordgenerator.data.repositories

import kotlinx.coroutines.flow.Flow
import ru.kuzmin.passwordgenerator.data.local.dao.PasswordDao
import ru.kuzmin.passwordgenerator.data.local.entities.PasswordEntity

class PasswordRepository(private val passwordDao: PasswordDao) {
    val allPasswords: Flow<List<PasswordEntity>> = passwordDao.getAllPasswords()

    suspend fun insert(password: PasswordEntity) {
        passwordDao.insert(password)
    }

    suspend fun update(password: PasswordEntity) {
        passwordDao.update(password)
    }

    suspend fun delete(password: PasswordEntity) {
        passwordDao.delete(password)
    }

    suspend fun getPasswordById(id: Int): PasswordEntity? {
        return passwordDao.getPasswordById(id)
    }
}