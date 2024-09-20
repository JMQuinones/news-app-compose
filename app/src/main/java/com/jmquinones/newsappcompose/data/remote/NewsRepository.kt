package com.jmquinones.newsappcompose.data.remote

import com.jmquinones.newsapp.models.NewsResponse
import okhttp3.Response
import java.util.Locale.IsoCountryCode
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsApi) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        newsApi.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        newsApi.searchForNews(searchQuery, pageNumber)
}