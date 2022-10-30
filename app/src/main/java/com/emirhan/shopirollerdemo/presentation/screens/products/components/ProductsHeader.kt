package com.emirhan.shopirollerdemo.presentation.screens.products.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emirhan.shopirollerdemo.core.Constants.Companion.DATE
import com.emirhan.shopirollerdemo.core.Constants.Companion.FILTER
import com.emirhan.shopirollerdemo.core.Constants.Companion.FILTER_BUTTON
import com.emirhan.shopirollerdemo.core.Constants.Companion.NAVIGATE_BACK
import com.emirhan.shopirollerdemo.core.Constants.Companion.PRICE
import com.emirhan.shopirollerdemo.core.Constants.Companion.PRODUCTS_HEADER
import com.emirhan.shopirollerdemo.core.Constants.Companion.SEARCH_ACTION
import com.emirhan.shopirollerdemo.core.Constants.Companion.SEARCH_RESULTS
import com.emirhan.shopirollerdemo.core.Constants.Companion.TITLE
import com.emirhan.shopirollerdemo.data.remote.dto.Products
import com.emirhan.shopirollerdemo.presentation.screens.products.ProductsViewModel
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun ProductsHeader(
    categoryID: String? = null,
    categoryName: String? = null,
    viewModel: ProductsViewModel = hiltViewModel(),
    action: String? = null,
    navigateBack: () -> Unit
) {
    var dropdownState by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(), elevation = 10.dp
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navigateBack() }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = NAVIGATE_BACK
                )
            }

            Text(
                text = categoryName
                    ?: if (action == SEARCH_ACTION) SEARCH_RESULTS else PRODUCTS_HEADER,
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .placeholder(
                        visible = categoryName == null && action != SEARCH_ACTION,
                        Color.LightGray,
                        highlight = PlaceholderHighlight.shimmer(
                            Color.White
                        )
                    ),
                style = MaterialTheme.typography.h2
            )

            Box {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    shape = CircleShape,
                    onClick = { dropdownState = !dropdownState },
                    modifier = Modifier
                        .clip(CircleShape)
                        .placeholder(
                            visible = categoryName == null && action != SEARCH_ACTION,
                            Color.LightGray,
                            highlight = PlaceholderHighlight.shimmer(
                                Color.White
                            )
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = FILTER_BUTTON,
                        tint = Color.Black
                    )
                    Text(
                        text = FILTER, color = Color.Black, style = MaterialTheme.typography.body2
                    )
                }

                DropdownMenu(
                    expanded = dropdownState,
                    onDismissRequest = { dropdownState = !dropdownState }) {
                    DropdownMenuItem(onClick = {
                        if (categoryID != null) {
                            getProductsWithSort(categoryID, PRICE, viewModel)
                        } else {
                            viewModel.sortSearchedProducts(PRICE)
                        }
                    }) {
                        Text(
                            text = PRICE, style = MaterialTheme.typography.body2
                        )
                    }
                    DropdownMenuItem(onClick = {
                        if (categoryID != null) {
                            getProductsWithSort(categoryID, TITLE, viewModel)
                        } else {
                            viewModel.sortSearchedProducts(TITLE)
                        }
                    }) {
                        Text(
                            text = TITLE, style = MaterialTheme.typography.body2
                        )
                    }
                    DropdownMenuItem(onClick = {
                        if (categoryID != null) {
                            getProductsWithSort(categoryID, DATE, viewModel)
                        } else {
                            viewModel.sortSearchedProducts(DATE)
                        }
                    }) {
                        Text(
                            text = DATE, style = MaterialTheme.typography.body2
                        )
                    }
                }
            }
        }
    }
}

fun getProductsWithSort(categoryID: String, sort: String, viewModel: ProductsViewModel) {
    viewModel.getProducts(categoryID, sort).also {
        viewModel.resetState()
    }
}