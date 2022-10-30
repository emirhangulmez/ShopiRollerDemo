package com.emirhan.shopirollerdemo.presentation.screens.category.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.emirhan.shopirollerdemo.R
import com.emirhan.shopirollerdemo.core.Constants
import com.emirhan.shopirollerdemo.data.remote.dto.Category

@Composable
fun CategoryItem(
    navigateToProducts: (String) -> Unit,
    categoryItem: Category
) {
    Surface(
        contentColor = Color.Black
    ) {
        Card(
            Modifier
                .widthIn(100.dp, 130.dp)
                .padding(20.dp)
                .clickable {
                    navigateToProducts(categoryItem.categoryId)
                },
            elevation = 10.dp,
            backgroundColor = Color.White
        ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(130.dp, 170.dp),
                        model = ImageRequest.Builder(LocalContext.current)
                            .placeholder(R.drawable.loading_light)
                            .data(categoryItem.icon)
                            .crossfade(true)
                            .build(),
                        contentScale = ContentScale.Fit,
                        contentDescription = Constants.CATEGORY_IMAGE
                    )
                }
                Text(categoryItem.name)
            }
        }
    }
}
