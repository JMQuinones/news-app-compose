package com.jmquinones.newsappcompose.ui.components.breakingnews

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jmquinones.newsappcompose.data.models.Article
import com.jmquinones.newsappcompose.data.models.Source
import com.jmquinones.newsappcompose.R
import com.jmquinones.newsappcompose.ui.components.newslist.NewsItem
import com.jmquinones.newsappcompose.ui.components.newslist.NewsList
import com.jmquinones.newsappcompose.ui.navigation.NewsDetail
import com.jmquinones.newsappcompose.ui.theme.NewsAppComposeTheme
import com.jmquinones.newsappcompose.viewmodel.NewsViewModel

@Composable
fun BreakingNews(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val breakingNews by remember {
        viewModel.breakingNews
    }
    /*LazyColumn(modifier = modifier.padding(16.dp)) {
        items(breakingNews) { item ->
            //NewsItem()
            NewsItem(article = item) {
                navController.navigate(NewsDetail(item))
            }
        }
    }*/
    NewsList(navController = navController, items = breakingNews)
}



@Preview
@Composable
private fun PreviewNewsItem() {
    NewsAppComposeTheme {
        NewsItem(
            article = Article(
                1,
                "Jon Doe",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent at gravida tortor.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent at gravida tortor.",
                "2024-09-19T18:00:00Z",
                Source("1", "BBC"), "Lorem ipsum", "", ""

            )
        ) {

        }
    }
}