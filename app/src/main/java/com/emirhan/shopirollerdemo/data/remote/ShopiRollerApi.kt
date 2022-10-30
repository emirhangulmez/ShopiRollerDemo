package com.emirhan.shopirollerdemo.data.remote

import com.emirhan.shopirollerdemo.core.Constants.Companion.ALIAS_KEY
import com.emirhan.shopirollerdemo.core.Constants.Companion.API_KEY
import com.emirhan.shopirollerdemo.core.Constants.Companion.CATEGORIES_ENDPOINT
import com.emirhan.shopirollerdemo.core.Constants.Companion.PRODUCTS_ENDPOINT
import com.emirhan.shopirollerdemo.core.Constants.Companion.PRODUCT_ENDPOINT
import com.emirhan.shopirollerdemo.data.remote.dto.CategoriesDto
import com.emirhan.shopirollerdemo.data.remote.dto.ProductDto
import com.emirhan.shopirollerdemo.data.remote.dto.ProductsDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ShopiRollerApi {
    @Headers(
        API_KEY,
        ALIAS_KEY
    )
    @GET(CATEGORIES_ENDPOINT)
    suspend fun getCategories(): CategoriesDto

    @Headers(
        API_KEY,
        ALIAS_KEY
    )
    @GET(PRODUCTS_ENDPOINT)
    suspend fun getProducts(
        @Query("categoryId") categoryID: String,
        @Query("sort") sort: String
    ): ProductsDto

    @Headers(
        API_KEY,
        ALIAS_KEY
    )
    @GET(PRODUCT_ENDPOINT)
    suspend fun getProduct(@Path("productID") productID: String): ProductDto
}