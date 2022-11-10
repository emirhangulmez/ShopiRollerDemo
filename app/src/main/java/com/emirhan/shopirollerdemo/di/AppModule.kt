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
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor
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

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideShopiRollerApi(okHttpClient: OkHttpClient): ShopiRollerApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ShopiRollerApi::class.java)
    }


    @Provides
    @Singleton
    fun provideShopiRollerRepository(api: ShopiRollerApi): ShopiRollerRepository {
        return ShopiRollerRepositoryImpl(api)
    }
}