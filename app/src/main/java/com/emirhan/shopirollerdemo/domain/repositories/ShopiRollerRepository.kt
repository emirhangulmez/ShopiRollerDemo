package com.emirhan.shopirollerdemo.domain.repositories

import com.emirhan.shopirollerdemo.data.remote.dto.CategoriesDto
import com.emirhan.shopirollerdemo.data.remote.dto.ProductDto
import com.emirhan.shopirollerdemo.data.remote.dto.ProductsDto

interface ShopiRollerRepository {
    suspend fun getCategories(): CategoriesDto

    suspend fun getProducts(categoryID: String, sort: String): ProductsDto

    suspend fun getProduct(productID: String): ProductDto
}