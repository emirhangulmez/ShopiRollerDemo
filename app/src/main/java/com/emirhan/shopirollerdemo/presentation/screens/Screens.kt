package com.emirhan.shopirollerdemo.presentation.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector


sealed class Screen(val route: String, val label: String, var icon: ImageVector) {
    object HomeScreen : Screen(
        "home_screen",
        "Home",
        Icons.Outlined.Home
    )
    object CategoryScreen :  Screen(
        "category_screen",
        "Categories",
        Icons.Outlined.List
    )
    object ProductScreen : Screen(
        "product_screen",
        "Product",
        Icons.Outlined.ShoppingCart
    )
    object ProductsScreen : Screen(
        "products_screen",
        "Products",
        Icons.Outlined.List
    )
    object ProductPreviewScreen : Screen(
        "product_preview_screen",
        "Product Preview",
        Icons.Outlined.List
    )
    object ProductSearchScreen : Screen(
        "product_search_screen",
        "Products",
        Icons.Outlined.List
    )
}
