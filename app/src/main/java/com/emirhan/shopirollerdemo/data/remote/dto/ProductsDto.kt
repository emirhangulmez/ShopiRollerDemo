package com.emirhan.shopirollerdemo.data.remote.dto

data class ProductsDto(
    var data: List<Products>? = null,
    val meta: Meta? = null,
    val success: Boolean = false,
    var isLoading : Boolean = false,
    var error: String? = null
)