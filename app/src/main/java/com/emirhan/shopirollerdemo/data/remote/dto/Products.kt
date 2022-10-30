package com.emirhan.shopirollerdemo.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class Products(
    val appId: String? = null,
    val brand: Brand? = null,
    val brandId: String? = null,
    val campaignPrice: Double? = null,
    val category: ProductCategory? = null,
    val categoryId: String? = null,
    val createDate: String? = null,
    val currency: String? = null,
    val description: String? = null,
    val endDate: String? = null,
    val featuredImage: FeaturedImage? = null,
    val id: String? = null,
    val images: List<Image>? = null,
    val isActive: Boolean? = null,
    val isPublished: Boolean? = null,
    val isUnLimitedStock: Boolean? = null,
    val itemType: String? = null,
    val maxQuantityPerOrder: Int? = null,
    val orderIndex: Int? = null,
    val price: Double? = null,
    val publishmentDate: String? = null,
    val shippingPrice: Double? = null,
    val stock: Int? = null,
    val stockCode: String? = null,
    val title: String? = null,
    val updateDate: String? = null,
    val useFixPrice: Boolean = false,
    val variantData: @RawValue List<Any>? = null,
    val videos: @RawValue List<Any>? = null
) : Parcelable