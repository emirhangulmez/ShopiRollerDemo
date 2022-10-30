package com.emirhan.shopirollerdemo.presentation.navigation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emirhan.shopirollerdemo.core.Constants.Companion.BASKET_ICON

@Composable
fun BasketFabButton(
    productCount: Int,
    onClick: () -> Unit
) {
    Box(
        Modifier
            .widthIn(40.dp)
            .heightIn(64.dp)
            .padding(top = 40.dp)
            .clip(CircleShape)
            .background(if (MaterialTheme.colors.isLight) Color(0xFF44607C) else Color(0xFF393D47))
            .clickable { onClick() }
    ) {
        if (productCount != 0) {
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = BASKET_ICON,
                modifier = Modifier
                    .padding(15.dp)
                    .graphicsLayer {
                        scaleX = 1.5f
                        scaleY = 1.5f
                    },
                tint = Color.White,
            )
            Text(
                text = "$productCount",
                color = Color.White,
                letterSpacing = 0.sp,
                style = MaterialTheme.typography.subtitle2,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(17.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.Red)
                    .graphicsLayer {
                        shadowElevation = 1.39f
                    }
            )
        } else {
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = BASKET_ICON,
                modifier = Modifier
                    .padding(15.dp)
                    .graphicsLayer {
                        scaleX = 1.5f
                        scaleY = 1.5f
                    },
                tint = Color.White,
            )
        }
    }
}