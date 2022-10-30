package com.emirhan.shopirollerdemo.domain.use_cases

import com.emirhan.shopirollerdemo.core.Resource
import com.emirhan.shopirollerdemo.data.remote.dto.ProductDto
import com.emirhan.shopirollerdemo.domain.model.ProductRoomModel
import com.emirhan.shopirollerdemo.domain.repositories.ShopiRollerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetProductsFromRoomUseCase @Inject constructor(
    private val repo: ShopiRollerRepository
) {
    operator fun invoke(products: List<ProductRoomModel>): Flow<Resource<List<ProductDto>>> = flow {
        try {
            val list = arrayListOf<ProductDto>()
            products.forEach { product ->
                emit(Resource.Loading())
                product.productID?.let { repo.getProduct(it) }?.let { list.add(it) }
            }
            emit(Resource.Success(list))
        } catch (e: IOException) {
            emit(Resource.Error(e.message.orEmpty()))
        }
    }
}