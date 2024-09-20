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

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository): ViewModel() {
    private val _breakingNews = mutableStateOf<List<Article>>(emptyList())
    val breakingNews: State<List<Article>> = _breakingNews
    var currentPage by mutableIntStateOf(1)

    init {
        loadBreakingNews()
    }
    private fun loadBreakingNews(){
        viewModelScope.launch {
            try {
                val res = newsRepository.getBreakingNews("us", currentPage)
                if (res.isSuccessful && res.body() != null){
                    _breakingNews.value = res.body()!!.articles
                }
            } catch (e: Exception) {
                // Handle error
                _breakingNews.value = emptyList()
            }

        }
    }
}