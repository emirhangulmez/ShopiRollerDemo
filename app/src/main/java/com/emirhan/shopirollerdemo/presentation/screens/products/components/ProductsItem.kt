package com.emirhan.shopirollerdemo.presentation.screens.products.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.emirhan.shopirollerdemo.R
import com.emirhan.shopirollerdemo.core.Constants.Companion.PRODUCT_IMAGE
import com.emirhan.shopirollerdemo.data.remote.dto.Products
import com.emirhan.shopirollerdemo.presentation.screens.components.ProductPrice

@Composable
fun ProductItem(
    item: Products, navigateProduct: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(25.dp),
        elevation = 10.dp
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .clickable { item.id?.let { navigateProduct(it) } },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(50.dp, 130.dp),
                model = ImageRequest.Builder(LocalContext.current).data(item.featuredImage?.n)
                    .placeholder(
                        if (MaterialTheme.colors.isLight) R.drawable.loading_light else R.drawable.loading_dark
                    ).crossfade(true).build(),
                contentDescription = PRODUCT_IMAGE
            )
            Text(
                text = item.title ?: "", textAlign = TextAlign.Center, fontSize = 15.sp
            )

            Box {
                ProductPrice(
                    campaignPrice = item.campaignPrice ?: 0.0,
                    productPrice = item.price ?: 0.0,
                    productCurrency = item.currency ?: ""
                )
            }
        }
    }
}