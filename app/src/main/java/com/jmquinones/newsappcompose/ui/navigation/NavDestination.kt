package com.jmquinones.newsappcompose.ui.navigation

import com.jmquinones.newsappcompose.data.models.Article
import kotlinx.serialization.Serializable

@Serializable
object AllNewsScreen

@Serializable
object SavedNewsScreen

@Serializable
object SearchNewsScreen

@Serializable
data class NewsDetail(val article: Article)