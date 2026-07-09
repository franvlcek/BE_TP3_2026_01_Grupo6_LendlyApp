package com.example.lendlyapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [UserEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}
