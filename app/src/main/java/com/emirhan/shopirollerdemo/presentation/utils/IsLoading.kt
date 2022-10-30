package com.emirhan.shopirollerdemo.presentation.utils

import com.emirhan.shopirollerdemo.data.remote.dto.CategoriesDto
import com.emirhan.shopirollerdemo.data.remote.dto.ProductDto
import com.emirhan.shopirollerdemo.data.remote.dto.ProductsDto
import java.util.UUID

object IsLoading {
    fun withCategories(state: CategoriesDto): Boolean =
        state.isLoading && state.data == null

    fun withProduct(state: ProductDto): Boolean =
        state.isLoading && state.data == null

    fun withProducts(state: ProductsDto): Boolean =
        state.isLoading && state.data == null

}