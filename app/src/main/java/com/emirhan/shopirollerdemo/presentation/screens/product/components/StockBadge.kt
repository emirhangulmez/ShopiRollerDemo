package com.emirhan.shopirollerdemo.presentation.screens.product.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.emirhan.shopirollerdemo.core.Constants.Companion.IN_STOCK
import com.emirhan.shopirollerdemo.core.Constants.Companion.NO_STOCK
import com.emirhan.shopirollerdemo.data.remote.dto.Product
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun ProductStockBadge(product: Product, modifier: Modifier) {
    val badgeColors = listOf(MaterialTheme.colors.primary, Color(0xFFFF5722), Color.Red)
    val stockState = remember { mutableStateOf(0) }

    when (product.stock) {
        !in 0..10 -> {
            // In Stock
            stockState.value = 0
        }
        in 1..10  -> {
            // Show Stock Number
            stockState.value = 1
        }
        0 -> {
            // No Stock
            stockState.value = 2
        }
    }

    Box(
        modifier = modifier
            .sizeIn(56.dp)
            .statusBarsPadding()
            .padding(5.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(badgeColors[stockState.value].copy(alpha = 0.7f))
            .padding(5.dp)
    ) {
        when (stockState.value) {
            0 -> {
                Text(
                    color = Color.White,
                    text = IN_STOCK,
                )
            }
            1 -> {
                Text(
                    color = Color.White,
                    text = "Only ${product.stock} left this item",
                )
            }
            else -> {
                Text(
                    color = Color.White,
                    text = NO_STOCK,
                )
            }
        }
    }
}