package com.emirhan.shopirollerdemo.data.remote.dto

data class CategoriesDto(
    val `data`: List<Category>? = null,
    val success: Boolean = false,
    var isLoading : Boolean = false,
    var error: String? = null,
)