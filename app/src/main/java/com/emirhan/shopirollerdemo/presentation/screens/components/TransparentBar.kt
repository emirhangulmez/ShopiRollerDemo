package com.emirhan.shopirollerdemo.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.statusBarsHeight

@Composable
fun TransparentStatusBar() {
    Box(
        Modifier
            .fillMaxWidth()
            .statusBarsHeight()
            .background(
                if (MaterialTheme.colors.isLight) Color.White.copy(alpha = 0.75f) else Color.Black.copy(
                    alpha = 0.75f
                )
            )
    )
}