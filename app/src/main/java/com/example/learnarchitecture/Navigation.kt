package com.example.learnarchitecture

import androidx.navigation.NavHostController

private object TodoScreens {
    const val TASKS_SCREEN = "tasks"
}

object TodoDestinationsArgs {
    const val USER_MESSAGE_ARG = "userMessage"
    const val TASK_ID_ARG = "taskId"
    const val TITLE_ARG = "title"
}

object Destinations {
    const val TASK_ROUTE = ""
    const val STATISTICS_ROUTE = ""
}

class NavigationAction(private val navController: NavHostController) {
    fun navigateToTasks() {
//        navController.navigate()
    }

    fun navigateToStatisticcs() {

    }
}