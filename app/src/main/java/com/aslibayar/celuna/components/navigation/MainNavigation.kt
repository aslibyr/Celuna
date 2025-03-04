package com.aslibayar.celuna.components.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aslibayar.celuna.ui.home.HomeScreen
import com.aslibayar.celuna.ui.profile.ProfileScreen

@Composable
fun MainNavigation(
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = modifier,
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(200, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Left
            )
        },
        exitTransition = {
            slideOutOfContainer(
                animationSpec = tween(200, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Left
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                animationSpec = tween(200, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Right
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                animationSpec = tween(200, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Right
            )
        }
    ) {
        composable<HomeRoute> {
            HomeScreen()
        }

        composable<ProfileRoute> {
            ProfileScreen()
        }
    }
}
