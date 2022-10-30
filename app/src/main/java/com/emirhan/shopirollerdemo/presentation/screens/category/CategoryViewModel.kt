package com.emirhan.shopirollerdemo.presentation.screens.category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirhan.shopirollerdemo.core.Resource
import com.emirhan.shopirollerdemo.data.remote.dto.CategoriesDto
import com.emirhan.shopirollerdemo.domain.repositories.ProductRepository
import com.emirhan.shopirollerdemo.domain.use_cases.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategories: GetCategoriesUseCase,
    val repo: ProductRepository
) : ViewModel() {

    var stateCategories by mutableStateOf(CategoriesDto())
        private set

    init {
        getCategoriesFromApi()
    }

    private fun getCategoriesFromApi() =
        getCategories().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let {
                        stateCategories = it
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
}