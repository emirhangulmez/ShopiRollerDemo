package com.emirhan.shopirollerdemo.presentation.screens.product.components

import android.view.Gravity
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import com.emirhan.shopirollerdemo.R

@Composable
fun ProductDescription(html: String, modifier: Modifier = Modifier) {
    val isLight = MaterialTheme.colors.isLight
    Column(modifier) {
        AndroidView(
            factory = { context ->
                TextView(context)
                    .apply {
                        gravity = Gravity.CENTER_HORIZONTAL
                        typeface = ResourcesCompat.getFont(context, R.font.poppins_regular)
                        textSize = 17f
                        if (isLight) {
                            setTextColor(ContextCompat.getColor(context, R.color.anthracite))
                        } else {
                            setTextColor(ContextCompat.getColor(context, R.color.chrome))
                        }
                    }
            },
            update = { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) }
        )
    }
}