package com.example.learnarchitecture.data.source

import androidx.annotation.VisibleForTesting
import com.example.learnarchitecture.data.Task
import kotlinx.coroutines.flow.*
import com.example.learnarchitecture.data.Result.Success
import com.example.learnarchitecture.data.Result.Error
import com.example.learnarchitecture.data.Result

class FakeRepository: TasksRepository {
    private var shouldReturnError = false

    private val _saveTasks = MutableStateFlow(LinkedHashMap<String, Task>())
    val savedTasks: StateFlow<LinkedHashMap<String, Task>> = _saveTasks.asStateFlow()

    private val observableTasks: Flow<Result<List<Task>>> = savedTasks.map {
        if (shouldReturnError) {
            Error(Exception()) as Result<List<Task>>
        } else {
            Success(it.values.toList()) as Result<List<Task>>
        }
    }

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun refreshTasks() {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTask(taskId: String) {
        refreshTasks()
    }

    override fun getTasksStream(): Flow<Result<List<Task>>> = observableTasks

//    override fun getTaskStream(taskId: String): Flow<Result<Task>> {
//        return observableTasks.map { tasks ->
//            when (tasks) {
//                is Error -> Error(tasks.exception)
//                is Success -> {
//                    val task = tasks.data.firstOrNull { it.id == taskId }
//                        ?: return@map Error(Exception("Not found"))
//                    Success(task)
//                }
//            }
//        }
//    }

    override suspend fun getTask(taskId: String, forceUpdate: Boolean): Result<Task> {
        if(shouldReturnError) {
            return Error(Exception("Test exception")) as Result<Task>
        }

        savedTasks.value[taskId]?.let {
            return Success(it) as Result<Task>
        }

        return Error(Exception("Could not find task")) as Result<Task>
    }

    override suspend fun getTasks(forceUpdate: Boolean): Result<List<Task>> {
        if (shouldReturnError) {
            return Error(Exception("Test exception")) as Result<List<Task>>
        }
        return observableTasks.first()
    }

    override suspend fun saveTask(task: Task) {
        _saveTasks.update { tasks ->
            val newTasks = LinkedHashMap<String, Task>(tasks)
            newTasks[task.id] = task
            newTasks
        }
    }

    override suspend fun completeTask(task: Task) {
        val completedTask = Task(task.title, task.description, true, task.id)
        _saveTasks.update { tasks ->
            val newTasks = LinkedHashMap<String, Task>(tasks)
            newTasks[task.id] = completedTask
            newTasks
        }
    }

    override suspend fun completeTask(taskId: String) {
        throw NotImplementedError()
    }

    override suspend fun activateTask(task: Task) {
        val activeTask = Task(task.title, task.description, false, task.id)
        _saveTasks.update { tasks ->
            val newTasks = LinkedHashMap<String, Task>(tasks)
            newTasks[task.id] = activeTask
            newTasks
        }
    }

    override suspend fun activateTask(taskId: String) {
        throw NotImplementedError()
    }

    override suspend fun clearCompletedTasks() {
        _saveTasks.update { tasks ->
            tasks.filterValues { !it.isCompleted } as LinkedHashMap<String, Task>
        }
    }

    override suspend fun deleteTask(taskId: String) {
        _saveTasks.update { tasks ->
            val newTasks = LinkedHashMap<String, Task>(tasks)
            newTasks.remove(taskId)
            newTasks
        }
    }

    override suspend fun deleteAllTasks() {
        _saveTasks.update {
            LinkedHashMap()
        }
    }

    @VisibleForTesting
    fun addTasks(vararg tasks: Task) {
        _saveTasks.update { oldTasks ->
            val newTasks = LinkedHashMap<String, Task>(oldTasks)
            for (task in tasks) {
                newTasks[task.id] = task
            }
            newTasks
        }
    }
}