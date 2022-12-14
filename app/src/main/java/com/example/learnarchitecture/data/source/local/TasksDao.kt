package com.example.learnarchitecture.data.source.local

import com.example.learnarchitecture.data.Task
import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Query

@Dao
interface TasksDao {
    @Query("SELECT * FROM tasks")
    fun observeTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE entryid = :taskId")
    fun observeTaskById(taskId: String): Flow<Task>

    @Query("SELECT * FROM tasks")
    suspend fun getTasks(): List<Task>


}