package com.emirhan.shopirollerdemo.presentation.screens.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat
import java.util.*

@Composable
fun ProductPrice(
    modifier: Modifier = Modifier,
    campaignPrice: Double = 0.0,
    productPrice: Double,
    productCurrency: String,
    action: String? = null
) {
    val currency = Currency.getInstance(productCurrency)
    val decimalFormat = DecimalFormat().apply {
        isDecimalSeparatorAlwaysShown = false
    }
    // Control Product Discount
    if (campaignPrice != 0.0) {
        Row {
            Text(
                text = "${decimalFormat.format(campaignPrice)} ${currency.symbol}",
                modifier = modifier.padding(3.dp),
                color = if (MaterialTheme.colors.isLight) Color.DarkGray else Color.LightGray,
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "${decimalFormat.format(productPrice)} ${currency.symbol}",
                modifier = modifier.padding(3.dp),
                fontSize = 14.sp,
                color = Color.Red.copy(alpha = 0.5f),
                style = MaterialTheme.typography.body2 + LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough)
            )
        }
    } else if (action == "total") {
        Text(
            text = "Total Price: ${decimalFormat.format(productPrice)} ${currency.symbol}",
            modifier = modifier,
            color = if (MaterialTheme.colors.isLight) Color.DarkGray else Color.LightGray,
            style = MaterialTheme.typography.body2
        )
    } else {
        Text(
            modifier = modifier,
            text = "${decimalFormat.format(productPrice)} ${currency.symbol}",
            color = if (MaterialTheme.colors.isLight) Color.DarkGray else Color.LightGray,
            style = MaterialTheme.typography.body2
        )
    }
}