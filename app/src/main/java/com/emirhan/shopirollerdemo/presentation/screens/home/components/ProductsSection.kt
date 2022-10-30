package com.emirhan.shopirollerdemo.presentation.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.emirhan.shopirollerdemo.R
import com.emirhan.shopirollerdemo.core.Constants.Companion.PRODUCT_IMAGE
import com.emirhan.shopirollerdemo.data.remote.dto.Products

@Composable
fun HomeProductsSection(
    navigateToProduct: (String) -> Unit,
    product: Products?,
    modifier: Modifier = Modifier,
    customTextSize: TextUnit = TextUnit.Unspecified,
    customHeight: Dp = 50.dp,
) {
    Card(
        modifier = modifier
            .padding(12.dp)
            .fillMaxWidth(),
        elevation = 10.dp,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            Modifier
                .widthIn(130.dp, 170.dp)
                .clickable {
                    product?.let {
                        product.id?.let { productID -> navigateToProduct(productID) }
                    }
                },
            horizontalAlignment = CenterHorizontally
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .heightIn(50.dp, 127.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .placeholder(if (MaterialTheme.colors.isLight) R.drawable.loading_light else R.drawable.loading_dark)
                        .data(product?.featuredImage?.n)
                        .crossfade(true)
                        .build(),
                    modifier = Modifier.fillMaxWidth(),
                    contentDescription = PRODUCT_IMAGE,
                )
            }

            Box(
                modifier = Modifier.height(customHeight),
                contentAlignment = Center,
            ) {
                Text(
                    text = product?.title ?: "",
                    textAlign = TextAlign.Center,
                    color = if (MaterialTheme.colors.isLight) Color.Black else Color.White,
                    style = MaterialTheme.typography.subtitle2,
                    fontSize = customTextSize,
                )
            }
        }
    }
}