package com.emirhan.shopirollerdemo.data.repositories

import com.emirhan.shopirollerdemo.data.remote.ShopiRollerApi
import com.emirhan.shopirollerdemo.data.remote.dto.CategoriesDto
import com.emirhan.shopirollerdemo.data.remote.dto.ProductDto
import com.emirhan.shopirollerdemo.data.remote.dto.ProductsDto
import com.emirhan.shopirollerdemo.domain.repositories.ShopiRollerRepository

class ShopiRollerRepositoryImpl(
    private val api: ShopiRollerApi
) : ShopiRollerRepository {
    override suspend fun getCategories(): CategoriesDto = api.getCategories()
    override suspend fun getProducts(categoryID: String, sort: String): ProductsDto =
        api.getProducts(categoryID, sort)

    override suspend fun getProduct(productID: String): ProductDto = api.getProduct(productID)
}