package com.emirhan.shopirollerdemo.presentation.screens.sheet.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.emirhan.shopirollerdemo.data.remote.dto.ProductDto
import com.emirhan.shopirollerdemo.domain.model.ProductRoomModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BasketProducts(
    productItems: List<ProductRoomModel>,
    products: SnapshotStateList<ProductDto?>,
) {
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.90f)
    ) {

        stickyHeader {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Divider(
                    Modifier
                        .size(width = 50.dp, height = 4.5.dp)
                        .clip(CircleShape),
                    color = MaterialTheme.colors.onBackground,
                    thickness = 2.dp
                )
            }
        }

        items(products) { product ->
            BasketProductItem(
                product = product,
                productItems = productItems,
            )
        }


        if (products.isEmpty() && productItems.isNotEmpty()) {
            items(productItems.size) {
                BasketProductItem(
                    productItems = productItems,
                )
            }
        }
    }
}
