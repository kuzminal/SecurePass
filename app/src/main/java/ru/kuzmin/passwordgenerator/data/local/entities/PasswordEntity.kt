package ru.kuzmin.passwordgenerator.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwords")
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val username: String,
    val encryptedPassword: String,
    val website: String?,
    val notes: String?,
    val createdAt: Long = System.currentTimeMillis()
)