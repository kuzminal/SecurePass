package ru.kuzmin.passwordgenerator.data.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.kuzmin.passwordgenerator.data.local.dao.PasswordDao
import ru.kuzmin.passwordgenerator.data.local.entities.PasswordEntity

@Database(entities = [PasswordEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun passwordDao(): PasswordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "passwords_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}