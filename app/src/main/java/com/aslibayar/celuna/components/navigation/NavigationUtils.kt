package com.aslibayar.celuna.components.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Profile : Screen("profile")
}

fun NavController.navigateToScreen(screen: Screen) {
    navigate(screen.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavDestination?.isScreen(screen: Screen): Boolean {
    return this?.hierarchy?.any { it.route == screen.route } == true
}
