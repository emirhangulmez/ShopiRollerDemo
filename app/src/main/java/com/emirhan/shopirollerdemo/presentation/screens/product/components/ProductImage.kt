package com.emirhan.shopirollerdemo.presentation.screens.product.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.emirhan.shopirollerdemo.core.Constants.Companion.ADDED_TO_BASKET
import com.emirhan.shopirollerdemo.core.Constants.Companion.ADD_TO_BASKET
import com.emirhan.shopirollerdemo.core.Constants.Companion.NAVIGATE_PRODUCTS
import com.emirhan.shopirollerdemo.core.Constants.Companion.PRODUCT_IMAGE
import com.emirhan.shopirollerdemo.core.Constants.Companion.REACHED_PRODUCTS
import com.emirhan.shopirollerdemo.data.remote.dto.Product
import com.emirhan.shopirollerdemo.domain.model.ProductRoomModel
import com.emirhan.shopirollerdemo.presentation.screens.components.getWidth
import com.emirhan.shopirollerdemo.presentation.screens.product.ProductViewModel
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch

@Composable
fun ProductImage(
    product: Product,
    scrollState: ScrollState,
    scaffoldState: ScaffoldState,
    navigateToProducts: () -> Unit,
    viewModel: ProductViewModel = hiltViewModel(),
) {
    // Coroutine Scope
    val scope = rememberCoroutineScope()
    // Scrim Feature for Product Image
    var sizeImage by remember { mutableStateOf(IntSize.Zero) }
    val gradient = Brush.verticalGradient(
        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f)),
        startY = sizeImage.height.toFloat() / 3,  // 1/3
        endY = sizeImage.height.toFloat(),
        tileMode = TileMode.Mirror
    )

    val productItemFromRoom by viewModel.repo.getProduct(product.id).collectAsState(initial = null)
    val basketVisibilityState = remember { MutableTransitionState(true) }
    var quantityState by remember { mutableStateOf(0) }

    LaunchedEffect(productItemFromRoom) {
        productItemFromRoom?.let { productRoomModel ->
                productRoomModel.quantity?.let { qty ->
                    if (qty >= 1) {
                        quantityState = qty
                        basketVisibilityState.targetState = false
                    } else {
                        basketVisibilityState.targetState = true
                    }
                }
            }
    }

    LaunchedEffect(quantityState) {
        productItemFromRoom.also { result ->
            result?.let { productDB ->
                productDB.quantity?.let { qty ->
                    if (qty >= 1) {
                        quantityState = qty
                        basketVisibilityState.targetState = false
                    } else {
                        basketVisibilityState.targetState = true
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.featuredImage.n)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(200.dp, 300.dp)
                // Image shrink on scrolling
                .graphicsLayer {
                    alpha = 1 - (scrollState.value / 600f)
                    rotationX = (-scrollState.value / 2) * 0.1f
                    translationX = (-scrollState.value / 2) * 0.1f
                }
                // Bottom Scrim
                .onGloballyPositioned {
                    sizeImage = it.size
                },
            contentDescription = PRODUCT_IMAGE
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(gradient)
        )

        // Icon Buttons
        IconButton(
            onClick = navigateToProducts::invoke,
            modifier = Modifier
                .sizeIn(56.dp)
                .statusBarsPadding()
                .padding(5.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                tint = Color.White,
                contentDescription = NAVIGATE_PRODUCTS
            )
        }

        if (!basketVisibilityState.currentState) {
            Box(
                Modifier
                    .width(getWidth(divisionValue = 3.0))
                    .align(Alignment.BottomEnd),
                contentAlignment = Alignment.BottomEnd
            ) {
                Row(Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                if (quantityState != 1) {
                                    viewModel.repo.decreaseQuantity(product.id)
                                } else {
                                    viewModel.repo.deleteProduct(product.id)
                                    basketVisibilityState.targetState = true
                                }
                            }
                        },
                        modifier = Modifier
                            .padding(end = 32.dp)
                            .sizeIn(56.dp)
                            .padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            tint = Color.White,
                            contentDescription = ADD_TO_BASKET
                        )
                    }
                }

                Text(
                    text = "$quantityState",
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(8.dp)
                        .padding(bottom = 8.dp)
                )

                IconButton(
                    onClick = {
                        scope.launch {
                            productItemFromRoom.let { productFromRoom ->
                                if (productFromRoom != null) {
                                    productFromRoom.let {
                                        productFromRoom.maxQuantityPerOrder?.let { maxQuantityPerOrder ->
                                            if (maxQuantityPerOrder >= quantityState) {
                                                viewModel.repo.increaseQuantity(product.id)
                                            } else {
                                                scaffoldState.snackbarHostState.showSnackbar(
                                                    message = REACHED_PRODUCTS
                                                )
                                            }
                                        }

                                    }
                                } else {
                                    viewModel.repo.addProduct(ProductRoomModel(productID = product.id))
                                }
                            }

                        }
                    },
                    modifier = Modifier
                        .sizeIn(56.dp)
                        .padding(8.dp)

                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        tint = Color.White,
                        contentDescription = ADD_TO_BASKET
                    )
                }
            }
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomEnd),
            visibleState = basketVisibilityState,
            enter = EnterTransition.None,
            exit = fadeOut(tween(300))
        ) {
            IconButton(
                onClick = {
                    scope.launch {
                            if (productItemFromRoom != null) {
                                viewModel.repo.increaseQuantity(product.id)
                            } else {
                                viewModel.repo.addProduct(ProductRoomModel(productID = product.id, maxQuantityPerOrder = product.maxQuantityPerOrder))
                            }


                        scaffoldState.snackbarHostState.showSnackbar(
                            message = ADDED_TO_BASKET
                        )

                    }
                },
                modifier = Modifier
                    .sizeIn(56.dp)
                    .padding(8.dp)

            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    tint = Color.White,
                    contentDescription = ADD_TO_BASKET
                )
            }
        }

        // Stock Badge
        ProductStockBadge(
            product = product,
            modifier = Modifier.align(Alignment.TopEnd)
        )
    }
}