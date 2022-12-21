package com.example.learnarchitecture

import androidx.navigation.NavHostController
import com.example.learnarchitecture.TodoScreens.ADD_EDIT_TASK_SCREEN
import com.example.learnarchitecture.TodoScreens.TASKS_SCREEN
import com.example.learnarchitecture.TodoDestinationsArgs.TASK_ID_ARG
import com.example.learnarchitecture.TodoScreens.STATISTICS_SCREEN
import com.example.learnarchitecture.TodoDestinationsArgs.USER_MESSAGE_ARG

private object TodoScreens {
    const val TASKS_SCREEN = "tasks"
    const val TASK_DETAIL_SCREEN = "task"
    const val STATISTICS_SCREEN = "statistics"
    const val ADD_EDIT_TASK_SCREEN = "addEditTask"
}

object TodoDestinationsArgs {
    const val USER_MESSAGE_ARG = "userMessage"
    const val TASK_ID_ARG = "taskId"
    const val TITLE_ARG = "title"
}

object Destinations {
    const val TASKS_ROUTE = "$TASKS_SCREEN?$USER_MESSAGE_ARG={$USER_MESSAGE_ARG}"
    const val STATISTICS_ROUTE = STATISTICS_SCREEN
}

class NavigationAction(private val navController: NavHostController) {
    fun navigateToTasks() {
//        navController.navigate()
    }

    fun navigateToStatisticcs() {

    }

    fun navigateToTaskDetail(taskId: String) {
        navController.navigate("$TASKS_SCREEN/$taskId")
    }

    fun navigateToAddEditTask(title: Int, taskId: String?) {
        navController.navigate("$ADD_EDIT_TASK_SCREEN/$title".let { if(taskId != null) "$it?$TASK_ID_ARG=$taskId" else it })
    }
}