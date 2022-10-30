package com.emirhan.shopirollerdemo.presentation.screens.sheet

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.emirhan.shopirollerdemo.domain.model.ProductRoomModel
import com.emirhan.shopirollerdemo.presentation.screens.sheet.components.BasketPriceSection
import com.emirhan.shopirollerdemo.presentation.screens.sheet.components.BasketProducts
import com.google.accompanist.insets.navigationBarsPadding

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BasketBottomSheetContent(
    viewModel: BasketBottomSheetViewModel = hiltViewModel(),
    sheetState: ModalBottomSheetState,
    productItems: List<ProductRoomModel>
) {
    if (sheetState.isVisible) {
        viewModel.getTotalPrice(productItems)

        LaunchedEffect(productItems) {
            viewModel.getTotalPrice(productItems)
            viewModel.getProductsFromList(productItems)
        }

        val products = viewModel.productsStateList
        val totalPrice = viewModel.totalPrice

        Column(
            Modifier
                .navigationBarsPadding()
                .fillMaxHeight(0.85f)
                .padding(top = 10.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
               BasketProducts(
                   productItems = productItems,
                   products = products
               )

                BasketPriceSection(
                    products = products,
                    productItems = productItems,
                    totalPrice = totalPrice
                )
            }
        }
    } else {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(Modifier.padding(10.dp))
        }
    }
}





