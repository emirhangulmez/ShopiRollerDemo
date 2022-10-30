package com.emirhan.shopirollerdemo.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.emirhan.shopirollerdemo.R
import com.emirhan.shopirollerdemo.core.Constants.Companion.PRODUCT_IMAGE
import com.emirhan.shopirollerdemo.data.remote.dto.Products
import com.google.accompanist.pager.*
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import java.text.DecimalFormat
import java.util.*
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomePagerSection(
    listProducts: List<Products>?,
    pagerState: PagerState,
    navigateToProduct: (String) -> Unit
) {
    if (listProducts.isNullOrEmpty()) {
        HorizontalPager(
            count = 3,
            state = pagerState
        ) { page ->
            Card(
                Modifier
                    .fillMaxWidth()
                    .heightIn(200.dp, 250.dp)
                    .padding(start = 27.dp, end = 27.dp)
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset =
                            calculateCurrentOffsetForPage(page).absoluteValue

                        // We animate the scaleX + scaleY, between 85% and 100%
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    .clip(RoundedCornerShape(15.dp)),
                shape = RoundedCornerShape(15.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .placeholder(
                            visible = true,
                            Color.Gray,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.White
                            )
                        )
                )
            }
        }
    } else {
        HorizontalPager(
            count = listProducts.size,
            state = pagerState
        ) { page ->
            val product = listProducts[page]

            Card(
                Modifier
                    .fillMaxWidth()
                    .heightIn(200.dp, 250.dp)
                    .padding(start = 27.dp, end = 27.dp)
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset =
                            calculateCurrentOffsetForPage(page).absoluteValue

                        // We animate the scaleX + scaleY, between 85% and 100%
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    },
                shape = RoundedCornerShape(15.dp)
            ) {
                product.let { product ->
                    HomePagerContent(product) {
                        product.id?.let { it1 -> navigateToProduct(it1) }
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))

    HorizontalPagerIndicator(
        pagerState = pagerState,
        modifier = Modifier.padding(10.dp)
    )
}

@Composable
fun HomePagerContent(
    product: Products,
    navigateToProduct: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                product.id?.let { navigateToProduct(it) }
            }
    ) {
        // Scrim Feature for Product Image
        var sizeImage by remember { mutableStateOf(IntSize.Zero) }
        val gradient = Brush.verticalGradient(
            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f)),
            startY = sizeImage.height.toFloat() / 3,  // 1/3
            endY = sizeImage.height.toFloat(),
            tileMode = TileMode.Mirror
        )
        // Card content
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .placeholder(R.drawable.loading_pager)
                .data(product.featuredImage?.n)
                .scale(Scale.FIT)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                // Bottom Scrim
                .onGloballyPositioned {
                    sizeImage = it.size
                },
            contentDescription = PRODUCT_IMAGE,
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(gradient)
        )

        product.title?.let {
            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(5.dp),
                text = it,
                color = Color.White,
                style = MaterialTheme.typography.h1
            )
        }
        val currency = Currency.getInstance(product.currency)
        val decimalFormat = DecimalFormat().apply {
            isDecimalSeparatorAlwaysShown = false
        }

        Text(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 5.dp, end = 20.dp),
            text = "Only ${decimalFormat.format(product.campaignPrice)} ${currency.symbol}",
            color = Color.White,
            style = MaterialTheme.typography.h2
        )

        Text(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 25.dp, end = 10.dp),
            text = "${decimalFormat.format(product.price)} ${currency.symbol}",
            color = Color.Red,
            textDecoration = TextDecoration.LineThrough,
            style = MaterialTheme.typography.body1
        )
    }
}
