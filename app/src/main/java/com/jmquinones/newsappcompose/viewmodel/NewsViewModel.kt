package com.jmquinones.newsappcompose.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmquinones.newsappcompose.data.models.Article
import com.jmquinones.newsappcompose.data.remote.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.jmquinones.newsappcompose.data.db.ArticleDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val db: ArticleDatabase
) : ViewModel() {

    private val articleDao = db.articleDao()

    private val _breakingNews = mutableStateOf<List<Article>>(emptyList())
    val breakingNews: State<List<Article>> = _breakingNews

    val breakingNewsPageFlow: Flow<PagingData<Article>> = newsRepository.getNewsPaged()

    private val _savedArticles = mutableStateOf<List<Article>>(emptyList())
    val savedArticles: State<List<Article>> = _savedArticles

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query


    var currentBreakingNewsPage by mutableIntStateOf(1)

    /*init {
        loadBreakingNews()
    }*/

    private fun loadBreakingNews() {
        viewModelScope.launch {
            try {
                val res = newsRepository.getBreakingNews("us", currentBreakingNewsPage)
                if (res.isSuccessful && res.body() != null) {
                    _breakingNews.value = res.body()!!.articles
                }
            } catch (e: Exception) {
                // Handle error
                _breakingNews.value = emptyList()
            }

        }
    }

    val searchResults = _query
        .debounce(300)
        .filter { it.isNotBlank() }
        .flatMapLatest { query ->
            newsRepository.getSearchedNewsPaged(query)
        }
        .cachedIn(viewModelScope)

    fun setQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun loadSavedArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            _savedArticles.value = articleDao.getAllArticles()
        }
    }

    fun saveArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            articleDao.saveArticle(article)
        }
    }
}