package com.emirhan.shopirollerdemo.presentation.navigation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.emirhan.shopirollerdemo.R
import com.emirhan.shopirollerdemo.presentation.screens.Screen

@Composable
fun BottomNavigationBar(
    navController: NavHostController, modifier: Modifier = Modifier
) {

    val homeScreen = Screen.HomeScreen.apply {
        icon = ImageVector.vectorResource(id = R.drawable.ic_home)
    }
    val categoryScreen = Screen.CategoryScreen.apply {
        icon = ImageVector.vectorResource(id = R.drawable.ic_category)
    }

    val items = listOf(
        homeScreen, categoryScreen
    )


    BottomAppBar(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = if (MaterialTheme.colors.isLight) Color(0xFF44607C) else Color(0xFF393D47),
        elevation = 15.dp,
        cutoutShape = CircleShape
    ) {
        items.forEach { item ->
            if (navController.currentDestination?.route == item.route) {
                BottomNavigationItem(
                    selected = navController.currentDestination?.route == item.route,
                    unselectedContentColor = Color.White,
                    onClick = {
                        if (navController.currentDestination?.route != item.route) {
                            navController.popBackStack()
                            navController.navigate(item.route)
                        }
                    },
                    label = {
                        Text(
                            text = "●",
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon, contentDescription = "", tint = Color.White
                        )
                    },
                    selectedContentColor = Color.White
                )
            } else {
                BottomNavigationItem(selected = navController.currentDestination?.route == item.route,
                    unselectedContentColor = Color.White.copy(alpha = 0.4f),
                    onClick = {
                        if (navController.currentDestination?.route != item.route) {
                            navController.navigate(item.route) {
                                navController.graph.startDestinationRoute?.let {
                                    popUpTo(it) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(imageVector = item.icon, contentDescription = item.label)
                    })
            }
        }
    }
}


