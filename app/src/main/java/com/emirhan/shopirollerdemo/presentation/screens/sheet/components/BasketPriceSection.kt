package com.emirhan.shopirollerdemo.presentation.screens.sheet.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.emirhan.shopirollerdemo.core.Constants
import com.emirhan.shopirollerdemo.data.remote.dto.ProductDto
import com.emirhan.shopirollerdemo.domain.model.ProductRoomModel
import com.emirhan.shopirollerdemo.presentation.screens.components.ProductPrice
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun BasketPriceSection(
    products: SnapshotStateList<ProductDto?>,
    productItems: List<ProductRoomModel>,
    totalPrice: Double
) {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(Constants.PAYMENT_URL)) }

    Row(
        Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.BottomCenter)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ProductPrice(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .placeholder(
                            visible = products.isEmpty() && productItems.isNotEmpty(),
                            Color.Gray,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.White
                            )
                        ),
                    productPrice = totalPrice,
                    productCurrency = products.map { it?.data?.currency }.getOrNull(0)
                        ?: Constants.USD,
                    action = Constants.TOTAL_ACTION
                )
                OutlinedButton(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .placeholder(
                            visible = products.isEmpty() && productItems.isNotEmpty(),
                            Color.Gray,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color.White
                            )
                        ),
                    onClick = { context.startActivity(intent) },
                ) {
                    Text(text = Constants.GO_TO_PAYMENT)
                }
            }
        }
    }
}