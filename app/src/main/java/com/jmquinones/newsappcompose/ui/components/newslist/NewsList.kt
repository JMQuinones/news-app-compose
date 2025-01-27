package com.jmquinones.newsappcompose.ui.components.newslist

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jmquinones.newsappcompose.R
import com.jmquinones.newsappcompose.data.models.Article
import com.jmquinones.newsappcompose.data.models.Source
import com.jmquinones.newsappcompose.ui.navigation.NewsDetail
import com.jmquinones.newsappcompose.ui.theme.NewsAppComposeTheme
import com.jmquinones.newsappcompose.viewmodel.NewsViewModel
import kotlinx.coroutines.launch

@Composable
fun NewsList(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel(),
    items: List<Article>
) {
    /*val breakingNews by remember {
        viewModel.breakingNews
    }*/
    LazyColumn(verticalArrangement = Arrangement.spacedBy(80.dp),modifier = modifier.padding(16.dp)) {
        items(items) { item ->
            //NewsItem()
            NewsItem(article = item) {
                navController.navigate(NewsDetail(item))
            }
        }
    }
}

@Composable
fun NewsListPaged(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel(),
    items: LazyPagingItems<Article>,
    snackbarHostState: SnackbarHostState
) {
    /*val breakingNews by remember {
        viewModel.breakingNews
    }*/
    val localCoroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    when {
        items.loadState.refresh is LoadState.Loading && items.itemCount == 0 -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp), color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        items.loadState.refresh is LoadState.NotLoading && items.itemCount == 0 -> {
            LaunchedEffect(Unit) {
                snackbarHostState.showSnackbar(
                    message = context.getString(R.string.no_articles)
                )
            }
        }
        /*items.loadState.hasError -> {
            LaunchedEffect(Unit) {
                snackbarHostState.showSnackbar(
                    message = context.getString(R.string.error)
                )
            }
        }*/

        else -> {
            LazyColumn(modifier = modifier.padding(16.dp)) {
                items(items.itemCount) {
                    items[it]?.let { article ->
                        //NewsItem()
                        NewsItem(article = article) {
                            navController.navigate(NewsDetail(article))
                        }
                    }
                }
            }
            if (items.loadState.append is LoadState.Loading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp), color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }

    }
    /*LazyColumn(modifier = modifier.padding(16.dp)) {
        items(items.itemCount) { items[it]?.let { article ->
            //NewsItem()
            NewsItem(article = article) {
                navController.navigate(NewsDetail(article))
            }
        }
        }
    }*/
}

@Composable
fun NewsItem(article: Article, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Spacer(modifier = Modifier.height(4.dp))
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(8.dp)
            .clickable { onClick() }) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(article.urlToImage)
                .crossfade(true)
                .build(),
            contentDescription = article.title,
            onSuccess = {

            },
            placeholder = painterResource(id = R.drawable.ic_newspaper),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = article.title.orEmpty(),
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = article.description.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    text = article.source?.name.orEmpty(),
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(
                    modifier = Modifier
                        .width(8.dp)
                        .border(1.dp, color = Color.LightGray)
                )
                Text(
                    text = article.publishedAt.orEmpty(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

    }
    Spacer(modifier = Modifier.height(4.dp))

}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
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