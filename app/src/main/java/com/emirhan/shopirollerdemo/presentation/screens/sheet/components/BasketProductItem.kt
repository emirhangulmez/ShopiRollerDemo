package com.emirhan.shopirollerdemo.presentation.screens.sheet.components

import android.content.ContentValues.TAG
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.emirhan.shopirollerdemo.core.Constants
import com.emirhan.shopirollerdemo.core.Constants.Companion.PRICE_DEFAULT
import com.emirhan.shopirollerdemo.data.remote.dto.ProductDto
import com.emirhan.shopirollerdemo.domain.model.ProductRoomModel
import com.emirhan.shopirollerdemo.presentation.screens.components.ProductPrice
import com.emirhan.shopirollerdemo.presentation.screens.components.getWidth
import com.emirhan.shopirollerdemo.presentation.screens.sheet.BasketBottomSheetViewModel
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import kotlinx.coroutines.launch

@Composable
fun BasketProductItem(
    product: ProductDto? = null,
    viewModel: BasketBottomSheetViewModel = hiltViewModel(),
    productItems: List<ProductRoomModel>
    ) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            Row(Modifier.fillMaxWidth()) {
                product?.let {
                    AsyncImage(
                        modifier = Modifier
                            .width(getWidth(divisionValue = 2.5))
                            .padding(10.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(product.data?.featuredImage?.n)
                            .scale(Scale.FIT)
                            .crossfade(true)
                            .build(),
                        contentDescription = Constants.PRODUCT_IMAGE
                    )

                Column {
                    Text(
                        text = "${product.data?.title}",
                    )
                    product.data?.campaignPrice?.let { campaignPrice ->
                        product.data?.price?.let { price ->
                            product.data?.currency?.let { currency ->
                                ProductPrice(
                                    campaignPrice = campaignPrice,
                                    productPrice = price,
                                    productCurrency = currency
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                product.data?.id?.let {
                                    scope.launch {
                                        if (productItems.find { it.productID == product.data?.id }?.quantity != 1) {
                                            scope.launch {
                                                product.data?.let {
                                                    viewModel.repo.decreaseQuantity(it.id)
                                                }
                                            }
                                        } else {
                                            scope.launch {
                                                product.data?.let {
                                                    viewModel.repo.deleteProduct(it.id)
                                                    viewModel.deleteProduct(it.id)
                                                }
                                            }
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .sizeIn(56.dp)
                                .padding(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                tint = if (MaterialTheme.colors.isLight) Color.Black else Color.White,
                                contentDescription = Constants.ADD_TO_BASKET
                            )
                        }

                        Text(
                            text = "${productItems.find { it.productID == product.data?.id }?.quantity}",
                            modifier = Modifier
                                .padding(8.dp)
                                .padding(top = 3.dp)
                        )

                        IconButton(
                            onClick = {
                                product.data?.id?.let {
                                    scope.launch {
                                        product.data?.let { product ->
                                            productItems.find { it.productID == product.id }?.maxQuantityPerOrder?.let { maxQuantityPerOrder ->
                                                productItems.find { it.productID == product.id }?.quantity?.let { qty ->
                                                    if (maxQuantityPerOrder >= qty) {
                                                        viewModel.repo.increaseQuantity(
                                                            product.id
                                                        )
                                                    } else {
                                                        Toast.makeText(
                                                            context,
                                                            Constants.REACHED_PRODUCTS,
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                }
                                            }
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
                                tint = if (MaterialTheme.colors.isLight) Color.Black else Color.White,
                                contentDescription = Constants.ADD_TO_BASKET
                            )
                        }
                    }
                }
            } ?: BasketProductEmptyItem()
            }
    }
    Divider()
}

@Composable
fun BasketProductEmptyItem() {
    AsyncImage(
        modifier = Modifier
            .width(154.dp)
            .height(120.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .placeholder(
                visible = true,
                Color.Gray,
                highlight = PlaceholderHighlight.shimmer(
                    highlightColor = Color.White
                )
            ),
        model = ImageRequest.Builder(LocalContext.current)
            .data("")
            .scale(Scale.FIT)
            .crossfade(true)
            .build(),
        contentDescription = Constants.PRODUCT_IMAGE
    )

    Column {
        // Title
        Text(
            text = TAG,
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(10.dp))
                .placeholder(
                    visible = true,
                    Color.Gray,
                    highlight = PlaceholderHighlight.shimmer(
                        highlightColor = Color.White
                    )
                )
        )
        // Price
        Text(
            text = PRICE_DEFAULT,
            modifier = Modifier
                .padding(start = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .placeholder(
                    visible = true,
                    Color.Gray,
                    highlight = PlaceholderHighlight.shimmer(
                        highlightColor = Color.White
                    )
                ),
            style = MaterialTheme.typography.body2
        )
    }
}