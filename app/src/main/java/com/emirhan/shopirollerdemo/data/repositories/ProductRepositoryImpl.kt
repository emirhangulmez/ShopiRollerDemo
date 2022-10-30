package com.emirhan.shopirollerdemo.data.repositories

import com.emirhan.shopirollerdemo.data.network.ProductDao
import com.emirhan.shopirollerdemo.domain.model.ProductRoomModel
import com.emirhan.shopirollerdemo.domain.repositories.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ProductRepositoryImpl(
    private val productDao: ProductDao
) : ProductRepository {
    override fun getProducts(): Flow<List<ProductRoomModel>> = productDao.getProducts()

    override fun getProduct(productID: String): Flow<ProductRoomModel> =
        productDao.getProduct(productID)

    override fun deleteProducts() = productDao.deleteProducts()

    override suspend fun addProduct(product: ProductRoomModel) = withContext(Dispatchers.IO) {
        productDao.addProduct(product)
    }

    override fun updateProduct(product: ProductRoomModel) = productDao.updateProduct(product)

    override suspend fun increaseQuantity(productID: String) = withContext(Dispatchers.IO) {
        productDao.increaseQuantity(productID)
    }

    override suspend fun decreaseQuantity(productID: String) = withContext(Dispatchers.IO) {
        productDao.decreaseQuantity(productID)
    }

    override suspend fun deleteProduct(productID: String) = withContext(Dispatchers.IO) {
        productDao.deleteProduct(productID)
    }
}