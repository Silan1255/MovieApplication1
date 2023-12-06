package com.example.movieapplication1.data.di

import com.example.movieapplication1.utils.Constants
import com.example.movieapplication.utils.Utils
import com.example.movieapplication1.data.api.ApplicationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideApplicationService(): ApplicationService = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(Utils.client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ApplicationService::class.java)
}
