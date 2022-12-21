package com.example.learnarchitecture

import android.util.Log
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.learnarchitecture.TodoDestinationsArgs.USER_MESSAGE_ARG
import com.example.learnarchitecture.tasks.TasksScreen
import com.example.learnarchitecture.util.Drawer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    ),
    coroutinneScope: CoroutineScope = rememberCoroutineScope(),
    navAction: NavigationAction = remember(navController) {
        NavigationAction(navController)
    },
    startDestination: String = Destinations.TASKS_ROUTE
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination
    NavHost(navController = navController, startDestination = startDestination) {
        composable(
            Destinations.TASKS_ROUTE,
            arguments = listOf(navArgument(USER_MESSAGE_ARG) {
                type = NavType.IntType; defaultValue = 0
            })
        ) { entry ->
            Drawer(
                drawerState = drawerState,
                currentRoute = currentRoute,
                navigationAction = navAction
            ) {
                TasksScreen(
                    userMessage = entry.arguments?.getInt(USER_MESSAGE_ARG)!!,
                    onAddTask = { navAction.navigateToAddEditTask(R.string.add_task, null) },
                    onTaskClick = { task -> navAction.navigateToTaskDetail(task.id) },
                    onUserMessageDisplayed = { entry.arguments?.putInt(USER_MESSAGE_ARG, 0) },
                    openDrawer = { coroutinneScope.launch { drawerState.open() } })
            }
        }
    }
}