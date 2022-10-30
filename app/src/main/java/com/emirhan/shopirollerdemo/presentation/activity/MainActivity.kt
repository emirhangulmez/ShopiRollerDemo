package com.emirhan.shopirollerdemo.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.emirhan.shopirollerdemo.presentation.navigation.NavGraph
import com.emirhan.shopirollerdemo.presentation.theme.ShopiRollerDemoTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ShopiRollerDemo()
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ShopiRollerDemo() {
    ShopiRollerDemoTheme {
        ProvideWindowInsets {
            val systemUiController = rememberSystemUiController()
            val navController = rememberAnimatedNavController()
            val isLight = MaterialTheme.colors.isLight

            SideEffect {
                systemUiController.setStatusBarColor(
                    color = Color.Transparent,
                    darkIcons = isLight
                )
                systemUiController.setNavigationBarColor(
                    color = if (isLight) Color(0xFF44607C) else Color(0xFF393D47),
                    darkIcons = false
                )
            }

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background
            ) {
                Scaffold { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavGraph(
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}