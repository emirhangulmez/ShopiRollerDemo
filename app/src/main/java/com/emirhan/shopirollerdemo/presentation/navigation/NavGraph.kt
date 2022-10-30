package com.emirhan.shopirollerdemo.presentation.navigation


import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import com.emirhan.shopirollerdemo.core.Constants.Companion.CATEGORY_ID
import com.emirhan.shopirollerdemo.core.Constants.Companion.PRODUCT_ID
import com.emirhan.shopirollerdemo.data.remote.dto.Products
import com.emirhan.shopirollerdemo.presentation.screens.Screen
import com.emirhan.shopirollerdemo.presentation.screens.category.CategoryScreen
import com.emirhan.shopirollerdemo.presentation.screens.home.HomeScreen
import com.emirhan.shopirollerdemo.presentation.screens.product.ProductScreen
import com.emirhan.shopirollerdemo.presentation.screens.products.ProductsSearchResultScreen
import com.emirhan.shopirollerdemo.presentation.screens.products.ProductsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@ExperimentalAnimationApi
@Composable
fun NavGraph(
    navController: NavHostController
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        addHome(navController)
        addCategoryScreen(navController)
        addProductScreen(navController)
        addProductsScreen(navController)
        addProductSearchScreen(navController)
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addHome(
    navController: NavHostController
) {
    composable(
        route = Screen.HomeScreen.route,
        enterTransition = {
            slideInHorizontally(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            if (initialState.destination.route == Screen.CategoryScreen.route) {
                ExitTransition.None
            } else {
                slideOutHorizontally(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        },
    ) {
        rememberSystemUiController().setStatusBarColor(
            color = if (MaterialTheme.colors.isLight) Color.Transparent else MaterialTheme.colors.background,
            darkIcons = MaterialTheme.colors.isLight
        )
        rememberSystemUiController().setNavigationBarColor(
            color = if (MaterialTheme.colors.isLight) Color(0xFF44607C) else Color(0xFF393D47),
            darkIcons = false
        )
        HomeScreen(
            navigateToProducts = { categoryID ->
                navController.navigate("${Screen.ProductsScreen.route}/${categoryID}")
            },
            navigateToProduct = { productID ->
                navController.navigate("${Screen.ProductScreen.route}/${productID}")
            },
            navController = navController,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.addCategoryScreen(
    navController: NavHostController
) {
    composable(
        route = Screen.CategoryScreen.route,

        enterTransition = {
            slideInHorizontally(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            if (initialState.destination.route == Screen.HomeScreen.route) {
                ExitTransition.None
            } else {
                slideOutHorizontally(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        },
    ) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(
            color = if (MaterialTheme.colors.isLight) Color.Transparent else MaterialTheme.colors.background,
            darkIcons = MaterialTheme.colors.isLight
        )
        systemUiController.setNavigationBarColor(
            color = if (MaterialTheme.colors.isLight) Color(0xFF44607C) else Color(0xFF393D47),
            darkIcons = false
        )
        CategoryScreen(
            navigateToProducts = { categoryID ->
                navController.navigate("${Screen.ProductsScreen.route}/${categoryID}")
            },
            navController = navController,
        )
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addProductsScreen(
    navController: NavHostController
) {
    composable(
        route = "${Screen.ProductsScreen.route}/{$CATEGORY_ID}",
        arguments = listOf(
            navArgument(CATEGORY_ID) {
                type = NavType.StringType
            }
        ),
        enterTransition = {
            slideInVertically(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        }
    ) { backStackEntry ->
        rememberSystemUiController().setStatusBarColor(
            color = Color.Transparent,
            darkIcons = MaterialTheme.colors.isLight
        )
        rememberSystemUiController().setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = false
        )
        val categoryID = backStackEntry.arguments?.getString(CATEGORY_ID) ?: ""
        ProductsScreen(
            categoryID = categoryID,
            navigateProduct = { productID -> navController.navigate("${Screen.ProductScreen.route}/${productID}") },
            navigateBack = { navController.popBackStack() }
        )
    }
}


@ExperimentalAnimationApi
fun NavGraphBuilder.addProductSearchScreen(
    navController: NavHostController
) {
    composable(
        route = Screen.ProductSearchScreen.route,
        enterTransition = {
            slideInVertically(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        }
    ) {
        rememberSystemUiController().setStatusBarColor(
            color = Color.Transparent,
            darkIcons = MaterialTheme.colors.isLight
        )
        rememberSystemUiController().setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = false
        )
        ProductsSearchResultScreen(
            products = navController.previousBackStackEntry?.savedStateHandle?.get<List<Products>>("products"),
            navigateProduct = { productID -> navController.navigate("${Screen.ProductScreen.route}/${productID}") },
            viewModel = hiltViewModel(),
            navigateBack = { navController.popBackStack() }
        )
    }
}

@ExperimentalAnimationApi
fun NavGraphBuilder.addProductScreen(
    navController: NavHostController
) {
    composable(
        route = "${Screen.ProductScreen.route}/{$PRODUCT_ID}",
        arguments = listOf(
            navArgument(PRODUCT_ID) {
                type = NavType.StringType
            }
        ),
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentScope.SlideDirection.Down,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(300))
        }
    ) { backStackEntry ->
        rememberSystemUiController().setStatusBarColor(
            color = Color.Transparent,
            darkIcons = false
        )
        rememberSystemUiController().setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = false
        )
        val productID = backStackEntry.arguments?.getString(PRODUCT_ID) ?: ""
        ProductScreen(
            productID = productID,
            navigateToProducts = { navController.popBackStack() }
        )
    }
}