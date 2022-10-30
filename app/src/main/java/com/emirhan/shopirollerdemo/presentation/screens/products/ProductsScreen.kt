package com.emirhan.shopirollerdemo.presentation.screens.products

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emirhan.shopirollerdemo.core.Constants.Companion.SEARCH_ACTION
import com.emirhan.shopirollerdemo.data.remote.dto.Products
import com.emirhan.shopirollerdemo.presentation.screens.components.Loading
import com.emirhan.shopirollerdemo.presentation.screens.components.TransparentStatusBar
import com.emirhan.shopirollerdemo.presentation.screens.products.components.ProductItem
import com.emirhan.shopirollerdemo.presentation.screens.products.components.ProductsHeader
import com.emirhan.shopirollerdemo.presentation.utils.IsLoading
import com.emirhan.shopirollerdemo.presentation.utils.LazyListScope.gridItems

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductsScreen(
    categoryID: String,
    viewModel: ProductsViewModel = hiltViewModel(),
    navigateProduct: (String) -> Unit,
    navigateBack: () -> Unit,
) {
    val productsState = viewModel.productsState
    val categoryName = productsState.data?.getOrNull(0)?.category?.name

    LaunchedEffect(Unit) {
        viewModel.getProducts(categoryID)
    }

    Scaffold(
        Modifier.fillMaxSize()
    ) { paddingValues ->

        if (IsLoading.withProducts(productsState)) {
            Loading()
        }
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            stickyHeader {
                TransparentStatusBar()
                ProductsHeader(
                    categoryID = categoryID,
                    categoryName = categoryName,
                    navigateBack = navigateBack
                )
            }

            productsState.data?.let { items ->
                gridItems(items, 2) { item ->
                    ProductItem(item) {
                        item.id?.let { productID -> navigateProduct(productID) }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.padding(36.dp))
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductsSearchResultScreen(
    products: List<Products>? = null,
    navigateProduct: (String) -> Unit,
    viewModel: ProductsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    LaunchedEffect(Unit) { products?.let {
        viewModel.searchedProducts.clear()
        viewModel.searchedProducts.addAll(products)
    } }

    Scaffold(
        Modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            stickyHeader {
                TransparentStatusBar()
                ProductsHeader(
                    navigateBack = navigateBack,
                    action = SEARCH_ACTION
                )
            }
            gridItems(viewModel.searchedProducts, 2) { product ->
                ProductItem(product) {
                    product.id?.let { productID -> navigateProduct(productID) }
                }
            }

            item {
                Spacer(modifier = Modifier.padding(36.dp))
            }
        }
    }
}