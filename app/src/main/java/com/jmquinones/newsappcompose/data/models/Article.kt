package com.jmquinones.newsappcompose.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "articles")
@kotlinx.serialization.Serializable
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
): Serializable