package com.emirhan.shopirollerdemo.presentation.screens.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emirhan.shopirollerdemo.data.remote.dto.Products
import com.emirhan.shopirollerdemo.data.remote.dto.ProductsDto
import com.emirhan.shopirollerdemo.presentation.navigation.components.BottomNavigationBar
import com.emirhan.shopirollerdemo.presentation.screens.sheet.BasketBottomSheetContent
import com.emirhan.shopirollerdemo.presentation.navigation.components.BasketFabButton
import com.emirhan.shopirollerdemo.presentation.screens.components.TransparentStatusBar
import com.emirhan.shopirollerdemo.presentation.screens.home.components.*
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalPagerApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class
)
@Composable
fun HomeScreen(
    navigateToProducts: (String) -> Unit,
    navigateToProduct: (String) -> Unit,
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val pagerState = rememberPagerState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)

    viewModel.stateCategories.also { state ->
        swipeRefreshState.isRefreshing = state.isLoading
    }

    val featuredProducts = viewModel.stateProductsFeatured.also { state ->
        swipeRefreshState.isRefreshing = state.isLoading
    }

    val productsState = remember { viewModel.productsState }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val productItems by viewModel.repo.getProducts().collectAsState(initial = emptyList())

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxSize(),
        sheetShape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
        sheetState = sheetState,
        sheetContent = {
            BasketBottomSheetContent(
                sheetState = sheetState,
                productItems = productItems
            )
        },
    ) {
        Scaffold(
            Modifier
                .fillMaxSize(),
            bottomBar = { BottomNavigationBar(navController, Modifier.navigationBarsPadding()) },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                BasketFabButton(productCount = productItems.size) {
                    scope.launch {
                        if (sheetState.isVisible) sheetState.hide() else sheetState.show()
                    }
                }
            }
        ) { paddingValues ->
            SwipeRefresh(
                modifier = Modifier.fillMaxSize(),
                state = swipeRefreshState,
                onRefresh = {
                    scope.launch {
                        viewModel.refreshProducts()
                    }
                },
                indicatorPadding = PaddingValues(top = 120.dp),
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        // Pass the SwipeRefreshState + trigger through
                        state = state,
                        refreshTriggerDistance = trigger,
                        // Enable the scale animation
                        scale = true,
                        // Change the color and shape
                        backgroundColor = MaterialTheme.colors.primary,
                        shape = MaterialTheme.shapes.small,
                    )
                }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    stickyHeader {
                        TransparentStatusBar()
                    }

                    item {
                        HomeHeader(
                            paddingValues = paddingValues,
                            navigateToProduct = navigateToProduct,
                            navController = navController
                        )
                    }

                    item {
                        HomePagerSection(
                            listProducts = featuredProducts.data,
                            pagerState = pagerState,
                            navigateToProduct = navigateToProduct
                        )
                    }

                    item {
                        productsState.forEach { productList ->
                            productList.data?.let { products ->
                                // Find and get product category and title from string length
                                val longCategory =
                                    products.find { it.title?.length!! >= 22 }?.category
                                val longProduct =
                                    products.find { it.title?.length!! >= 22 }?.title

                                HomeCategorySection(
                                    productList = productList,
                                    navigateToProducts = navigateToProducts
                                )

                                LazyRow(Modifier.fillMaxWidth()) {
                                    items(products) { product ->
                                        HomeProductsSection(
                                            navigateToProduct = navigateToProduct,
                                            product = product,
                                            modifier = Modifier.animateItemPlacement(
                                                animationSpec = tween(
                                                    durationMillis = 600
                                                )
                                            ),
                                            customTextSize = if (product.title != longProduct) 15.sp else 14.sp,
                                            customHeight = if (product.category != longCategory) 30.dp else 50.dp,
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Placeholders
                    if (productsState.isEmpty()) {
                        items(5) {
                            HomeCategorySection(
                                productList = ProductsDto(),
                                navigateToProducts = navigateToProducts,
                                modifier = Modifier
                                    .padding(start = 15.dp)
                                    .placeholder(
                                        visible = true,
                                        Color.Gray,
                                        highlight = PlaceholderHighlight.shimmer(
                                            highlightColor = Color.White
                                        )
                                    )
                            )

                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                items(5) {
                                    HomeProductsSection(
                                        navigateToProduct = {},
                                        product = Products(),
                                        modifier = Modifier
                                            .width(200.dp)
                                            .height(175.dp)
                                            .padding(12.dp)
                                            .clip(RoundedCornerShape(20.dp))
                                            .placeholder(
                                                visible = true,
                                                Color.Gray,
                                                highlight = PlaceholderHighlight.shimmer(
                                                    Color.White
                                                )
                                            )
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.padding(top = 120.dp))
                    }
                }
            }
        }
    }
}
