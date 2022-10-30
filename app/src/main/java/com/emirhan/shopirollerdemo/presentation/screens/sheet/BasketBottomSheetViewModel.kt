package com.emirhan.shopirollerdemo.presentation.screens.sheet

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhan.shopirollerdemo.core.Resource
import com.emirhan.shopirollerdemo.data.remote.dto.ProductDto
import com.emirhan.shopirollerdemo.domain.model.ProductRoomModel
import com.emirhan.shopirollerdemo.domain.model.ProductsState
import com.emirhan.shopirollerdemo.domain.repositories.ProductRepository
import com.emirhan.shopirollerdemo.domain.use_cases.GetProductsFromRoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketBottomSheetViewModel @Inject constructor(
    private val getProductUseCase: GetProductsFromRoomUseCase,
    val repo: ProductRepository
) : ViewModel() {

    private var productsState by mutableStateOf(ProductsState())

    var productsStateList = mutableStateListOf<ProductDto?>()
        private set

    var totalPrice by mutableStateOf(0.0)

    fun getProductsFromList(product: List<ProductRoomModel>) {
        productsStateList.clear()
        viewModelScope.launch {
            getProductUseCase(product).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        productsState.isLoading = false
                        result.data?.let { dto ->
                            productsStateList.addAll(dto)
                        }
                    }
                    is Resource.Loading -> {
                        productsState.isLoading = true
                    }
                    is Resource.Error -> {
                        productsState.error = result.message
                    }
                }
            }
        }
    }

    fun getTotalPrice(addedProducts: List<ProductRoomModel>?) {
        totalPrice = 0.0
        productsStateList.forEach { product ->
            val campaignPrice = product?.data?.campaignPrice
            if (campaignPrice != 0.0) {
                if (campaignPrice != null) {
                    addedProducts?.find { it.productID == product.data?.id }?.let {
                        it.quantity?.let { qty ->
                            totalPrice += campaignPrice * qty
                        }
                    }
                }
            } else {
                product.data?.price?.let { price ->
                    addedProducts?.find { it.productID == product.data?.id }?.let {
                        it.quantity?.let { qty ->
                            totalPrice += price * qty
                        }
                    }
                }
            }
        }
    }

    fun deleteProduct(productID: String) {
        productsStateList.find { it?.data?.id == productID }?.let {
            productsStateList.remove(it)
        }
    }
}
