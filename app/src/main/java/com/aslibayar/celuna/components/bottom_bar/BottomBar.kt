package com.aslibayar.celuna.components.bottom_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.aslibayar.celuna.components.navigation.Screen
import com.aslibayar.celuna.components.navigation.isScreen
import com.aslibayar.celuna.components.navigation.navigateToScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun BottomBar(
    navController: NavController,
    isBottomBarVisible: Boolean
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        BottomBarItem(
            name = "Home",
            screen = Screen.Home,
            icon = Icons.Outlined.Home
        ),
        BottomBarItem(
            name = "Profile",
            screen = Screen.Profile,
            icon = Icons.Outlined.Person
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        AnimatedVisibility(isBottomBarVisible) {
            NavigationBar(
                containerColor = Color.White,
                modifier = Modifier
                    .height(110.dp)
                    .clip(shape = RoundedCornerShape(30.dp))
                    .shadow(elevation = 10.dp, shape = RoundedCornerShape(30.dp)),
            ) {
                items.forEach { item ->
                    val selected = currentDestination.isScreen(item.screen)

                    NavigationBarItem(
                        selected = selected,
                        onClick = { navController.navigateToScreen(item.screen) },
                        label = {
                            androidx.compose.animation.AnimatedVisibility(
                                visible = selected,
                                enter = androidx.compose.animation.fadeIn() +
                                        androidx.compose.animation.expandVertically(),
                                exit = androidx.compose.animation.fadeOut() +
                                        androidx.compose.animation.shrinkVertically()
                            ) {
                                Text(
                                    text = item.name,
                                    fontSize = 10.sp,
                                    modifier = Modifier.animateContentSize()
                                )
                            }
                        },
                        icon = {
                            Icon(item.icon, contentDescription = item.name, Modifier.size(30.dp))
                        },
                        interactionSource = NoRippleInteractionSource,
                        colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                            unselectedTextColor = MaterialTheme.colorScheme.onBackground,
                            indicatorColor = Color.White
                        )
                    )
                }
            }
        }
    }
}

private object NoRippleInteractionSource : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = true
}