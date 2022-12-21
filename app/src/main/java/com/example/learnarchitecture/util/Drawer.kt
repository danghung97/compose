package com.example.learnarchitecture.util

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.learnarchitecture.Destinations
import com.example.learnarchitecture.NavigationAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.unit.dp

@Composable
fun Drawer(
    drawerState: DrawerState,
    currentRoute: String,
    navigationAction: NavigationAction,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    content: @Composable () -> Unit
) {
    ModalDrawer(drawerContent = {
        AppDrawer(
            currentRoute = currentRoute,
            navigateToTasks = { navigationAction.navigateToTasks() },
            navigateToStatistics = { navigationAction.navigateToStatisticcs() },
            closeDrawer = { coroutineScope.launch { drawerState.close() } },
        )
    }) {
        content()
    }
}

@Composable
private fun AppDrawer(
    currentRoute: String,
    navigateToTasks: () -> Unit,
    navigateToStatistics: () -> Unit,
    closeDrawer: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        DrawerButton(
            action = { navigateToTasks(); closeDrawer() },
            label = "List tasks",
            isSelected = currentRoute == Destinations.TASKS_ROUTE
        )
        Spacer(modifier = Modifier.height(10.dp))
        DrawerButton(
            action = { navigateToStatistics(); closeDrawer() },
            label = "List tasks",
            isSelected = currentRoute == Destinations.TASKS_ROUTE
        )
    }
}

@Composable
private fun DrawerButton(action: () -> Unit, label: String, isSelected: Boolean) {
    val tintColor = if (isSelected) {
        MaterialTheme.colors.secondary
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
    }
    TextButton(onClick = { action() }) {
        Text(text = label, color = tintColor, style = MaterialTheme.typography.h2)
    }
}