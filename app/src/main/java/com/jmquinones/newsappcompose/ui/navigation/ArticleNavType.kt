package com.jmquinones.newsappcompose.ui.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.jmquinones.newsappcompose.data.models.Article
import com.jmquinones.newsappcompose.data.models.Source
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object ArticleNavType {
    val ArticleType = object : NavType<Article>(
        isNullableAllowed = true
    ) {
        override fun get(bundle: Bundle, key: String): Article? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Article {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun put(bundle: Bundle, key: String, value: Article) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: Article): String {
            return Uri.encode(Json.encodeToString(value))
        }
    }
    val SourceType = object : NavType<Source>(
        isNullableAllowed = true
    ) {
        override fun get(bundle: Bundle, key: String): Source? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): Source {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun put(bundle: Bundle, key: String, value: Source) {
            bundle.putString(key, Json.encodeToString(value))
        }

        override fun serializeAsValue(value: Source): String {
            return Uri.encode(Json.encodeToString(value))
        }

    }
}