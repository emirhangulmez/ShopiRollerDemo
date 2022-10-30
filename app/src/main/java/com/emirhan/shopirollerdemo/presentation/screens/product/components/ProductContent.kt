package com.emirhan.shopirollerdemo.presentation.screens.product.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.emirhan.shopirollerdemo.data.remote.dto.Product
import com.emirhan.shopirollerdemo.presentation.screens.components.ProductPrice

@Composable
fun ProductContent(
    product: Product,
    paddingValues: PaddingValues,
    scaffoldState: ScaffoldState,
    navigateToProducts: () -> Unit
) {
    // For the image shrink feature on scrolling
    val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(Modifier.fillMaxSize()) {
            ProductImage(
                product = product,
                scrollState = scrollState,
                scaffoldState = scaffoldState,
                navigateToProducts = navigateToProducts
            )

        }

        Text(
            text = product.title,
            modifier = Modifier.padding(5.dp),
            style = MaterialTheme.typography.h1,
        )

        ProductPrice(
            campaignPrice = product.campaignPrice,
            productPrice = product.price,
            productCurrency = product.currency
        )

        Divider(Modifier.padding(5.dp))

        ProductDescription(
            modifier = Modifier.padding(5.dp),
            html = product.description,
        )
    }
}
