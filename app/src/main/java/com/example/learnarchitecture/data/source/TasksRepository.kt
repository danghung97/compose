package com.example.learnarchitecture.data.source

import com.example.learnarchitecture.data.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getTasksStream(): Flow<Result<List<Task>>>

    suspend fun getTasks(forceUpdate: Boolean = false): Result<List<Task>>

    suspend fun refreshTasks()

    suspend fun getTask(taskId: String, forceUpdate: Boolean = false): Result<Task>

    suspend fun refreshTask(taskId: String)

    suspend fun saveTask(task: Task)

    suspend fun completeTask(task: Task)

    suspend fun completeTask(taskId: String)

    suspend fun activateTask(task: Task)

    suspend fun activateTask(taskId: String)

    suspend fun clearCompletedTasks()

    suspend fun deleteAllTasks()

    suspend fun deleteTask(taskId: String)}