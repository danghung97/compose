package com.example.learnarchitecture.tasks

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.learnarchitecture.data.Task

@Composable
fun TasksScreen(
    @StringRes userMessage: Int,
    onAddTask: () -> Unit,
    onTaskClick: (Task) -> Unit,
    onUserMessageDisplayed: () -> Unit,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(scaffoldState = scaffoldState, topBar = {
//        TasksTopAppBar(
//            openDrawer = openDrawer,
//            onFilterAllTasks = { viewModel.setFiltering(ALL_TASKS) },
//            onFilterActiveTasks = { viewModel.setFiltering(ACTIVE_TASKS) },
//            onFilterCompletedTasks = { viewModel.setFiltering(COMPLETED_TASKS) },
//            onClearCompletedTasks = { viewModel.clearCompletedTasks() },
//            onRefresh = { viewModel.refresh() }
//        )
    },
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
//            FloatingActionButton(onClick = onAddTask) {
//                Icon(Icons.Filled.Add, stringResource(id = R.string.add_task))
//            }
        }) { paddingValues ->
//        val uiState by viewModel
        Log.d("ssss", "s ${paddingValues}")
    }
}