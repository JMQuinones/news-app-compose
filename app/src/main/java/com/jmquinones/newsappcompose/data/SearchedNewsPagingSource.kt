package com.jmquinones.newsappcompose.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jmquinones.newsappcompose.data.models.Article
import com.jmquinones.newsappcompose.data.remote.NewsApi
import com.jmquinones.newsappcompose.utils.Constants.QUERY_PAGE_SIZE
import java.io.IOException
import javax.inject.Inject

class SearchedNewsPagingSource @Inject constructor(private val newsApi: NewsApi, private val query: String): PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: 1
            val response = newsApi.searchForNews(query, page)

            if (response.isSuccessful){
                val news = response.body()
                val prevKey = if(page > 0) page -1 else null
                val nextKey = if(QUERY_PAGE_SIZE * page <= news!!.totalResults) page+1 else null
                LoadResult.Page(data = news.articles, prevKey, nextKey)
            } else {
                LoadResult.Error(Exception("Error fetching news"))
            }


        } catch (e: IOException){
            Log.e("SearchedNewsPagingSource", e.toString())
            LoadResult.Error(e)
        }
    }
}