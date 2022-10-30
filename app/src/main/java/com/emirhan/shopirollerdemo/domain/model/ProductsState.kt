package com.emirhan.shopirollerdemo.domain.model

import com.emirhan.shopirollerdemo.data.remote.dto.Product

data class ProductsState(
    var success: List<Product?>? = null,
    var isLoading: Boolean = false,
    var error: String? = null
)
