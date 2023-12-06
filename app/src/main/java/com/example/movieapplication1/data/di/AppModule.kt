package com.example.movieapplication1.data.di

import android.content.Context
import com.example.movieapplication1.utils.CustomToast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCustomToast(@ApplicationContext appContext: Context): CustomToast {
        return CustomToast(appContext)
    }
}