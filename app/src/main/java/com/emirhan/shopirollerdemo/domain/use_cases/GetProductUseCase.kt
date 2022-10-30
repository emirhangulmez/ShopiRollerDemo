package com.emirhan.shopirollerdemo.domain.use_cases

import com.emirhan.shopirollerdemo.core.Resource
import com.emirhan.shopirollerdemo.data.remote.dto.ProductDto
import com.emirhan.shopirollerdemo.domain.repositories.ShopiRollerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val repo: ShopiRollerRepository
) {
    operator fun invoke(productID: String): Flow<Resource<ProductDto>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(repo.getProduct(productID)))
        } catch (e: IOException) {
            emit(Resource.Error(e.message.toString()))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}