package com.pedro.environmentreader.data.di

import com.pedro.environmentreader.data.api.ImageAnalysisService
import com.pedro.environmentreader.data.repository.ImageAnalysisRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

sealed class DataModule {

    @Module
    @InstallIn(SingletonComponent::class)
    class SingletonModule {

        @Provides
        @Singleton
        fun provideRetrofit(): Retrofit {
            // TODO get baseUrl from gradle
            return Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideImageAnalysisService(
            retrofit: Retrofit,
        ): ImageAnalysisService {
            return retrofit.create(ImageAnalysisService::class.java)
        }

        @Provides
        @Singleton
        fun provideImageAnalysisRepository(
            imageAnalysisService: ImageAnalysisService
        ): ImageAnalysisRepository {
            return ImageAnalysisRepository(imageAnalysisService)
        }
    }
}