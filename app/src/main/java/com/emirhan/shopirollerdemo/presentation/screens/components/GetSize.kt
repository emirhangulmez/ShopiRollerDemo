package com.emirhan.shopirollerdemo.presentation.screens.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun getWidth(divisionValue: Double): Dp {
    val conf = LocalConfiguration.current
    return (conf.screenWidthDp / divisionValue).dp
}

@Composable
fun getHeight(divisionValue: Double): Dp {
    val conf = LocalConfiguration.current
    return (conf.screenWidthDp / divisionValue).dp
}