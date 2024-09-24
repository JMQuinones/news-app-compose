package com.jmquinones.newsappcompose.viewmodel

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
import com.jmquinones.newsappcompose.data.db.ArticleDatabase
import kotlinx.coroutines.Dispatchers

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val db: ArticleDatabase
) : ViewModel() {

    private val articleDao = db.articleDao()

    private val _breakingNews = mutableStateOf<List<Article>>(emptyList())
    val breakingNews: State<List<Article>> = _breakingNews

    private val _savedNews = mutableStateOf<List<Article>>(emptyList())
    val savedNews: State<List<Article>> = _savedNews

    var currentPage by mutableIntStateOf(1)

    init {
        loadBreakingNews()
    }

    private fun loadBreakingNews() {
        viewModelScope.launch {
            try {
                val res = newsRepository.getBreakingNews("us", currentPage)
                if (res.isSuccessful && res.body() != null) {
                    _breakingNews.value = res.body()!!.articles
                }
            } catch (e: Exception) {
                // Handle error
                _breakingNews.value = emptyList()
            }

        }
    }

    fun loadSavedArticles(){
        viewModelScope.launch(Dispatchers.IO) {
            _savedNews.value = articleDao.getAllArticles()
        }
    }

    fun saveArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            articleDao.saveArticle(article)
        }
    }
}