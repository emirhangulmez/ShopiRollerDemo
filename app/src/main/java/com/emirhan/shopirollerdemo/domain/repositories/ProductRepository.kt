package com.emirhan.shopirollerdemo.domain.repositories

import com.emirhan.shopirollerdemo.domain.model.ProductRoomModel
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<List<ProductRoomModel>>

    fun deleteProducts()

    suspend fun addProduct(product: ProductRoomModel)

    fun updateProduct(product: ProductRoomModel)

    suspend fun increaseQuantity(productID: String)

    suspend fun decreaseQuantity(productID: String)

    suspend fun deleteProduct(productID: String)
}