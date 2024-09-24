package com.jmquinones.newsappcompose.ui.components.newslist

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jmquinones.newsappcompose.R
import com.jmquinones.newsappcompose.data.models.Article
import com.jmquinones.newsappcompose.ui.navigation.NewsDetail
import com.jmquinones.newsappcompose.viewmodel.NewsViewModel

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
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(items) { item ->
            //NewsItem()
            NewsItem(article = item) {
                navController.navigate(NewsDetail(item))
            }
        }
    }
}
@Composable
fun NewsItem(article: Article, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
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
}