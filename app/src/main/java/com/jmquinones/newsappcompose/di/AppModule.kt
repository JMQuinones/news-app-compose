package com.jmquinones.newsappcompose.di

import com.jmquinones.newsappcompose.data.remote.NewsApi
import com.jmquinones.newsappcompose.data.remote.NewsRepository
import com.jmquinones.newsappcompose.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)

    }
    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi): NewsRepository{
        return NewsRepository(newsApi)
    }
}