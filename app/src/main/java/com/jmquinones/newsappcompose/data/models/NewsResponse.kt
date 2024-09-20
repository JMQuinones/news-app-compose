package com.jmquinones.newsapp.models

import com.jmquinones.newsappcompose.data.models.Article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)