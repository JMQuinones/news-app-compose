package com.jmquinones.newsappcompose.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jmquinones.newsapp.models.NewsResponse
import com.jmquinones.newsappcompose.data.NewsPagingSource
import com.jmquinones.newsappcompose.data.models.Article
import com.jmquinones.newsappcompose.utils.Constants.PREFETCH_ITEMS
import com.jmquinones.newsappcompose.utils.Constants.QUERY_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import okhttp3.Response
import java.util.Locale.IsoCountryCode
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: NewsApi) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        newsApi.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        newsApi.searchForNews(searchQuery, pageNumber)


    fun getNewsPaged(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = QUERY_PAGE_SIZE, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                NewsPagingSource(newsApi)
            }
        ).flow
    }
}