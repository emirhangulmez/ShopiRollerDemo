package com.emirhan.shopirollerdemo.presentation.screens.products

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhan.shopirollerdemo.core.Constants.Companion.DATE
import com.emirhan.shopirollerdemo.core.Constants.Companion.PRICE
import com.emirhan.shopirollerdemo.core.Constants.Companion.TITLE
import com.emirhan.shopirollerdemo.core.Resource
import com.emirhan.shopirollerdemo.data.remote.dto.Products
import com.emirhan.shopirollerdemo.data.remote.dto.ProductsDto
import com.emirhan.shopirollerdemo.domain.use_cases.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
) : ViewModel() {

    var productsState by mutableStateOf(ProductsDto())
        private set

    var searchedProducts = mutableStateListOf<Products>()

    fun getProducts(categoryID: String, sort: String = "") {
        viewModelScope.launch(Dispatchers.Main) {
            getProductsUseCase(categoryID, sort).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            productsState = it
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

    fun sortSearchedProducts(sort: String) {
        when (sort) {
            PRICE -> {
                if (searchedProducts.any {it.price != 0.0}) {
                    searchedProducts.sortByDescending { it.price }
                } else {
                    searchedProducts.sortByDescending { it.price }
                }
            }
            TITLE -> {
                searchedProducts.sortBy { it.title }
            }
            DATE -> {
                searchedProducts.sortByDescending { it.createDate }
            }
        }

    }



    fun resetState() {
        productsState = ProductsDto()
    }
}