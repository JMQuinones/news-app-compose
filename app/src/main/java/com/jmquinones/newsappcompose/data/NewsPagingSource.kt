package com.jmquinones.newsappcompose.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jmquinones.newsapp.models.NewsResponse
import com.jmquinones.newsappcompose.data.models.Article
import com.jmquinones.newsappcompose.data.remote.NewsApi
import com.jmquinones.newsappcompose.utils.Constants.QUERY_PAGE_SIZE
import java.io.IOException
import javax.inject.Inject

class NewsPagingSource @Inject constructor(private val newsApi: NewsApi): PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: 1
            val response = newsApi.getBreakingNews("us", page)

            if (response.isSuccessful){
                val news = response.body()
                val prevKey = if(page > 0) page -1 else null
                val nextKey = if(QUERY_PAGE_SIZE * page <= news!!.totalResults) page+1 else null
                LoadResult.Page(data = news.articles, prevKey, nextKey)
            } else {
                LoadResult.Error(Exception("Error fetching news"))
            }


        } catch (e: IOException){
            LoadResult.Error(e)
        }
    }
}