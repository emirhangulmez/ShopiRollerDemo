package com.emirhan.shopirollerdemo.presentation.screens.product

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.emirhan.shopirollerdemo.presentation.screens.components.Loading
import com.emirhan.shopirollerdemo.presentation.screens.product.components.ProductContent
import com.emirhan.shopirollerdemo.presentation.utils.IsLoading

@Composable
fun ProductScreen(
    productID: String,
    navigateToProducts: () -> Unit,
    viewModel: ProductViewModel = hiltViewModel(),
) {
    val productState = viewModel.productState
    LaunchedEffect(Unit) {
        viewModel.getProduct(productID)
    }

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        scaffoldState = scaffoldState,
    ) { paddingValues ->
        if (IsLoading.withProduct(productState)) {
            Loading()
        }
        productState.data?.let { product ->
            ProductContent(product, paddingValues, scaffoldState) { navigateToProducts() }
        }
    }
}