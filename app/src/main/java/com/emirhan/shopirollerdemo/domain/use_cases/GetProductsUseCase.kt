package com.emirhan.shopirollerdemo.domain.use_cases

import com.emirhan.shopirollerdemo.core.Resource
import com.emirhan.shopirollerdemo.data.remote.dto.ProductsDto
import com.emirhan.shopirollerdemo.domain.repositories.ShopiRollerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repo: ShopiRollerRepository
    ) {
    operator fun invoke(categoryID: String, sort: String = ""): Flow<Resource<ProductsDto>> = flow {
        try {
            emit(Resource.Loading())
            emit(Resource.Success(repo.getProducts(categoryID, sort)))
        } catch (e: IOException) {
            emit(Resource.Error(e.message.toString()))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}