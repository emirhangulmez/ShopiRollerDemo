package com.emirhan.shopirollerdemo.presentation.screens.home

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhan.shopirollerdemo.core.Constants.Companion.FEATURED_CATEGORY_ID
import com.emirhan.shopirollerdemo.core.Resource
import com.emirhan.shopirollerdemo.data.remote.dto.CategoriesDto
import com.emirhan.shopirollerdemo.data.remote.dto.Products
import com.emirhan.shopirollerdemo.data.remote.dto.ProductsDto
import com.emirhan.shopirollerdemo.domain.repositories.ProductRepository
import com.emirhan.shopirollerdemo.domain.use_cases.GetCategoriesUseCase
import com.emirhan.shopirollerdemo.domain.use_cases.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCategories: GetCategoriesUseCase,
    private val getProducts: GetProductsUseCase,
    val repo: ProductRepository
) : ViewModel() {

    var stateCategories by mutableStateOf(CategoriesDto())
        private set

    var productsState = mutableStateListOf<ProductsDto>()
        private set

    var stateProductsFeatured by mutableStateOf(ProductsDto())
        private set

    var searchState = mutableStateListOf<Products>()
        private set

    var expanded by mutableStateOf(false)

    var searchText by mutableStateOf("")

    init {
        getFeaturedProducts()
        getAllCategories()
    }

    private fun getFeaturedProducts(categoryID: String = FEATURED_CATEGORY_ID) {
        getProducts(categoryID).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        stateProductsFeatured = it
                    }
                }
                is Resource.Loading -> {
                    stateProductsFeatured.isLoading = true
                }
                is Resource.Error -> {
                    stateProductsFeatured.error = result.message
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getAllProducts(categoryID: String) =
        viewModelScope.launch {
            getProducts(categoryID).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            productsState.add(it)
                        }
                    }
                    is Resource.Loading -> {
                        stateProductsFeatured.isLoading = true
                    }
                    is Resource.Error -> {
                        stateProductsFeatured.error = result.message
                    }
                }
            }
        }


    private fun getAllCategories() =
        getCategories().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { categoriesDto ->
                        categoriesDto.data?.let { categories ->
                            categories.forEach { category ->
                                getAllProducts(category.categoryId)
                            }
                        }
                    }
                }
                is Resource.Loading -> {
                    stateCategories.isLoading = true
                }
                is Resource.Error -> {
                    stateCategories.error = result.message
                }
            }
        }.launchIn(viewModelScope)

    fun refreshProducts() {
        stateProductsFeatured = ProductsDto(isLoading = true)
        stateCategories = CategoriesDto(isLoading = true)
        productsState.clear()

        getFeaturedProducts()
        getAllCategories()
    }

    fun onSearchChanged(value: String) {
        searchText = value
        searchState.clear()
        if (value.length > 2) {
            productsState.forEach { productsState ->
                productsState.data?.let { productsDto ->
                    productsDto.forEach { products ->
                        if (products.title?.contains(value, true) == true) {
                            val newItems = searchState
                            newItems.addAll(listOf(products))
                            searchState = newItems.toSet().toMutableStateList()
                            expanded = true
                        }
                    }
                }
            }
        }
    }
}