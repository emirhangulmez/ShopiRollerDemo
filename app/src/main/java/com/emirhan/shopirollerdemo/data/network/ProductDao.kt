package com.emirhan.shopirollerdemo.data.network

import androidx.room.*
import com.emirhan.shopirollerdemo.core.Constants.Companion.PRODUCT_TABLE
import com.emirhan.shopirollerdemo.domain.model.ProductRoomModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM $PRODUCT_TABLE ORDER BY id ASC")
    fun getProducts(): Flow<List<ProductRoomModel>>

    @Query("SELECT * FROM $PRODUCT_TABLE WHERE productID = :productID")
    fun getProduct(productID: String): Flow<ProductRoomModel>

    @Query("DELETE FROM $PRODUCT_TABLE")
    fun deleteProducts()

    @Query("UPDATE $PRODUCT_TABLE SET quantity = quantity + 1 WHERE productID = :productID")
    fun increaseQuantity(productID: String)

    @Query("UPDATE $PRODUCT_TABLE SET quantity = quantity - 1 WHERE productID = :productID")
    fun decreaseQuantity(productID: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addProduct(product: ProductRoomModel)

    @Update
    fun updateProduct(product: ProductRoomModel)

    @Query("DELETE FROM $PRODUCT_TABLE WHERE productID = :productID")
    fun deleteProduct(productID: String)
}