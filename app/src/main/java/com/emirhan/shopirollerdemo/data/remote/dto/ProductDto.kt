package com.emirhan.shopirollerdemo.data.remote.dto

data class ProductDto(
    var data: Product? = null,
    val success: Boolean = false,
    var isLoading : Boolean = false,
    var error : String? = null
)