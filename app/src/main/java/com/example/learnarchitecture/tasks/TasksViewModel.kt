package com.example.learnarchitecture.tasks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.learnarchitecture.R
import com.example.learnarchitecture.data.Result
import com.example.learnarchitecture.data.Result.Success
import com.example.learnarchitecture.data.Task
import com.example.learnarchitecture.data.source.TasksRepository
import com.example.learnarchitecture.tasks.TasksFilterType.*
import com.example.learnarchitecture.util.Async
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

data class TasksUiState(
    val items: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val filteringUiInfo: FilteringUiInfo = FilteringUiInfo(),
    val userMessage: Int? = null,
)

data class FilteringUiInfo(
    val currentFilteringLabel: Int = R.string.label_all,
    val noTaskLabel: Int = R.string.no_tasks_all,
    val noTaskIconRes: Int = R.drawable.logo_no_fill
)

// Used to save the current filtering in SavedStateHandle.
const val TASKS_FILTER_SAVED_STATE_KEY = "TASKS_FILTER_SAVED_STATE_KEY"

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskRespository: TasksRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _savedFilterType =
        savedStateHandle.getStateFlow(TASKS_FILTER_SAVED_STATE_KEY, ALL_TASKS)

    private val _filterUiInfo = _savedFilterType.map { getFilterUiInfo(it) }
    private val _usermessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)
    private val _filteredTasksAsync =
        combine(taskRespository.getTasksStream(), _savedFilterType) { tasks, type ->
            filterTasks(tasks as Result<List<Task>>, type)
        }.map {
            Async.Success(it)
        }.onStart<Async<List<Task>>> { emit(Async.Loading) }

    private fun showSnackbarMessage(message: Int) {
        _usermessage.value = message
    }

    private fun filterTasks(
        tasksResult: Result<List<Task>>,
        filteringType: TasksFilterType
    ): List<Task> = if (tasksResult is Success) {
        filterItems(tasksResult.data, filteringType)
    } else {
        showSnackbarMessage(R.string.loading_tasks_error)
        emptyList()
    }

    private fun filterItems(tasks: List<Task>, filteringType: TasksFilterType): List<Task> {
        val tasksToShow = ArrayList<Task>()
        // We filter the tasks based on the requestType
        for (task in tasks) {
            when (filteringType) {
                ALL_TASKS -> tasksToShow.add(task)
                ACTIVE_TASKS -> if (task.isActive) {
                    tasksToShow.add(task)
                }
                COMPLETED_TASKS -> if (task.isCompleted) {
                    tasksToShow.add(task)
                }
            }
        }
        return tasksToShow
    }

    private fun getFilterUiInfo(requestType: TasksFilterType): FilteringUiInfo =
        when (requestType) {
            ALL_TASKS -> {
                FilteringUiInfo(R.string.label_all, R.string.no_tasks_all, R.drawable.logo_no_fill)
            }
            ACTIVE_TASKS -> {
                FilteringUiInfo(
                    R.string.label_active,
                    R.string.no_tasks_active,
                    R.drawable.ic_check_circle_96dp
                )
            }
            COMPLETED_TASKS -> {
                FilteringUiInfo(
                    R.string.label_completed,
                    R.string.no_tasks_completed,
                    R.drawable.ic_verified_user_96dp
                )
            }
        }


}