package com.jmquinones.newsappcompose.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.jmquinones.newsappcompose.data.models.Article

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles")
    fun getAllArticles(): List<Article>

    @Upsert
    fun saveArticle(vararg article: Article)

    @Delete
    fun delete(article: Article)
}