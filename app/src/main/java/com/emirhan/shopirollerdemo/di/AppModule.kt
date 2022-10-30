package com.emirhan.shopirollerdemo.di

import android.content.Context
import androidx.room.Room
import com.emirhan.shopirollerdemo.core.Constants.Companion.BASE_URL
import com.emirhan.shopirollerdemo.core.Constants.Companion.PRODUCT_TABLE
import com.emirhan.shopirollerdemo.data.network.ProductDao
import com.emirhan.shopirollerdemo.data.network.ProductDb
import com.emirhan.shopirollerdemo.data.remote.ShopiRollerApi
import com.emirhan.shopirollerdemo.data.repositories.ProductRepositoryImpl
import com.emirhan.shopirollerdemo.data.repositories.ShopiRollerRepositoryImpl
import com.emirhan.shopirollerdemo.domain.repositories.ProductRepository
import com.emirhan.shopirollerdemo.domain.repositories.ShopiRollerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideProductDB(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context,
        ProductDb::class.java,
        PRODUCT_TABLE
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideProductDao(
        productDb: ProductDb
    ) = productDb.productDao()

    @Provides
    @Singleton
    fun provideProductRepository(
        productDao: ProductDao
    ): ProductRepository = ProductRepositoryImpl(
        productDao = productDao
    )

    @Provides
    @Singleton
    fun provideShopiRollerApi(): ShopiRollerApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ShopiRollerApi::class.java)

    @Provides
    @Singleton
    fun provideShopiRollerRepository(api: ShopiRollerApi): ShopiRollerRepository {
        return ShopiRollerRepositoryImpl(api)
    }
}