package com.emirhan.shopirollerdemo.presentation.screens.category

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.emirhan.shopirollerdemo.core.Constants
import com.emirhan.shopirollerdemo.presentation.navigation.components.BottomNavigationBar
import com.emirhan.shopirollerdemo.presentation.screens.category.components.CategoryItem
import com.emirhan.shopirollerdemo.presentation.navigation.components.BasketFabButton
import com.emirhan.shopirollerdemo.presentation.screens.components.Loading
import com.emirhan.shopirollerdemo.presentation.screens.components.TransparentStatusBar
import com.emirhan.shopirollerdemo.presentation.screens.sheet.BasketBottomSheetContent
import com.emirhan.shopirollerdemo.presentation.utils.IsLoading
import com.emirhan.shopirollerdemo.presentation.utils.LazyListScope.gridItems
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryScreen(
    navigateToProducts: (String) -> Unit,
    navController: NavHostController,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val categoriesState = viewModel.stateCategories
    val isLoading = IsLoading.withCategories(categoriesState)
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val scope = rememberCoroutineScope()
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
            bottomBar = {
                BottomNavigationBar(
                    navController = navController,
                    modifier = Modifier.navigationBarsPadding()
                )
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            floatingActionButton = {
                BasketFabButton(productCount = productItems.size) {
                    scope.launch { if (sheetState.isVisible) sheetState.hide() else sheetState.show() }
                }
            }
        ) { paddingValues ->
            TransparentStatusBar()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(end = 20.dp, start = 20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                if (isLoading)
                    item { Loading() }

                categoriesState.data?.let { category ->
                    item {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .statusBarsPadding(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = Constants.SELECT_CATEGORY
                            )
                        }
                    }

                    gridItems(category, 2) { item ->
                        CategoryItem(
                            navigateToProducts = navigateToProducts,
                            categoryItem = item
                        )
                    }
                }
            }
        }
    }
}