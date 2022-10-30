package com.emirhan.shopirollerdemo.data.network

import androidx.room.Database
import androidx.room.RoomDatabase
import com.emirhan.shopirollerdemo.domain.model.ProductRoomModel

@Database(entities = [ProductRoomModel::class], version = 1, exportSchema = false)
abstract class ProductDb : RoomDatabase() {
    abstract fun productDao(): ProductDao
}