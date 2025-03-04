package com.aslibayar.celuna

import BottomBar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aslibayar.celuna.components.navigation.HomeRoute
import com.aslibayar.celuna.components.navigation.MainNavigation
import com.aslibayar.celuna.components.navigation.ProfileRoute
import com.aslibayar.celuna.ui.theme.CelunaTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.WHITE,
                android.graphics.Color.WHITE
            ),
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.WHITE,
                android.graphics.Color.WHITE
            ),
        )
        setContent {
            navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val showBottomBar by remember {
                derivedStateOf {
                    navBackStackEntry?.destination?.hasRoute<HomeRoute>() == true ||
                            navBackStackEntry?.destination?.hasRoute<ProfileRoute>() == true
                }
            }
            CelunaTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                    BottomBar(
                        navController = navController,
                        isBottomBarVisible = showBottomBar
                    )
                }) { innerPadding ->
                    MainNavigation(
                        navController = navController,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}
