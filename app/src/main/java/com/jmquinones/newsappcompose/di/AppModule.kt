package com.jmquinones.newsappcompose.di

import android.content.Context
import androidx.room.Room
import com.jmquinones.newsappcompose.data.db.ArticleDatabase
import com.jmquinones.newsappcompose.data.remote.NewsApi
import com.jmquinones.newsappcompose.data.remote.NewsRepository
import com.jmquinones.newsappcompose.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): ArticleDatabase {
         return Room.databaseBuilder(
            appContext,
            ArticleDatabase::class.java, "article_db.db"
        ).build()
    }
}