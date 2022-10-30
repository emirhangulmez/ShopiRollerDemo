package com.emirhan.shopirollerdemo.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ProductCategory(
    val categoryId: String,
    val createDate: String,
    val icon: Icon,
    val isActive: Boolean,
    val name: String,
    val orderIndex: Int,
    val subCategories: @RawValue List<Any>,
    val totalProducts: Int,
    val updateDate: String
) : Parcelable