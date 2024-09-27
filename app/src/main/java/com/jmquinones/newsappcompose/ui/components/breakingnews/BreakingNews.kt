package com.jmquinones.newsappcompose.ui.components.breakingnews

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.jmquinones.newsappcompose.data.models.Article
import com.jmquinones.newsappcompose.data.models.Source
import com.jmquinones.newsappcompose.ui.components.newslist.NewsItem
import com.jmquinones.newsappcompose.ui.components.newslist.NewsListPaged
import com.jmquinones.newsappcompose.ui.theme.NewsAppComposeTheme
import com.jmquinones.newsappcompose.viewmodel.NewsViewModel

@Composable
fun BreakingNews(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState
) {
    /*val breakingNews by remember {
        viewModel.breakingNews
    }*/
    /*LazyColumn(modifier = modifier.padding(16.dp)) {
        items(breakingNews) { item ->
            //NewsItem()
            NewsItem(article = item) {
                navController.navigate(NewsDetail(item))
            }
        }
    }*/
    val breakingNews = viewModel.breakingNewsPageFlow.collectAsLazyPagingItems()
    NewsListPaged(navController = navController, items = breakingNews, snackbarHostState = snackbarHostState)
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