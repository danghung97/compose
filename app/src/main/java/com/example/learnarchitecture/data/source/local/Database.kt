package com.example.learnarchitecture.data.source.local

import androidx.room.RoomDatabase
import com.example.learnarchitecture.data.Task

@androidx.room.Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class Database: RoomDatabase() {
    abstract fun taskDao(): TasksDao
}