package com.jmquinones.newsappcompose.ui.components.savedarticles

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jmquinones.newsappcompose.ui.components.newslist.NewsList
import com.jmquinones.newsappcompose.viewmodel.NewsViewModel

@Composable
fun SavedArticles(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel()
) {
    viewModel.loadSavedArticles()
    val savedArticles by remember {
        viewModel.savedArticles
    }
    /*LazyColumn(modifier = modifier.padding(16.dp)) {
        items(breakingNews) { item ->
            //NewsItem()
            NewsItem(article = item) {
                navController.navigate(NewsDetail(item))
            }
        }
    }*/
    NewsList(navController = navController, items = savedArticles)
}