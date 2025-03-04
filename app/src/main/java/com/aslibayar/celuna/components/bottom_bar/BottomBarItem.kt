package com.aslibayar.celuna.components.bottom_bar

import androidx.compose.ui.graphics.vector.ImageVector
import com.aslibayar.celuna.components.navigation.Screen

data class BottomBarItem(
    val name: String,
    val screen: Screen,
    val icon: ImageVector
)
