package com.emirhan.shopirollerdemo.presentation.screens.product

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhan.shopirollerdemo.core.Resource
import com.emirhan.shopirollerdemo.data.remote.dto.ProductDto
import com.emirhan.shopirollerdemo.domain.repositories.ProductRepository
import com.emirhan.shopirollerdemo.domain.use_cases.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    val repo: ProductRepository
) : ViewModel() {

    var productState by mutableStateOf(ProductDto())
        private set

    fun getProduct(productID: String) {
        getProductUseCase(productID).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { product ->
                        productState = product
                    }
                }
                is Resource.Loading -> {
                    productState.isLoading = true
                }
                is Resource.Error -> {
                    productState.error = result.message
                }
            }
        }.launchIn(viewModelScope)
    }
}