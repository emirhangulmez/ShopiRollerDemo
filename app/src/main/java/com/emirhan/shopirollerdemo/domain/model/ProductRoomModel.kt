package com.emirhan.shopirollerdemo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emirhan.shopirollerdemo.core.Constants.Companion.PRODUCT_TABLE

@Entity(tableName = PRODUCT_TABLE)
data class ProductRoomModel(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val productID: String? = null,
    var quantity: Int? = 1,
    val maxQuantityPerOrder: Int? = null
)