package com.emirhan.shopirollerdemo.presentation.screens.home.components

import android.content.ContentValues.TAG
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.emirhan.shopirollerdemo.data.remote.dto.ProductsDto

@Composable
fun HomeCategorySection(
    productList: ProductsDto,
    navigateToProducts: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    productList.data?.getOrNull(0)?.category?.let { category ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)
                .clickable {
                    productList.data?.let {
                        navigateToProducts(category.categoryId)
                    }
                }
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .align(Alignment.TopStart),
                textAlign = TextAlign.Start
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp),
            )
        }
    }

    if (productList.data.isNullOrEmpty()) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = modifier
                    .align(Alignment.TopStart)
                    .widthIn(100.dp, 150.dp)
            ) {
                Text(
                    text = TAG,
                )
            }
        }
    }
}