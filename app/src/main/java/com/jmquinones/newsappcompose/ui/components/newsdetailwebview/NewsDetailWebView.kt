package com.jmquinones.newsappcompose.ui.components.newsdetailwebview

import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jmquinones.newsappcompose.R
import com.jmquinones.newsappcompose.data.models.Article
import com.jmquinones.newsappcompose.viewmodel.NewsViewModel
import kotlinx.coroutines.launch

@Composable
fun NewsDetailWebView(
    article: Article,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    newsViewModel: NewsViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
) {
    val url = article.url.orEmpty()
    val localCoroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Box(modifier = modifier.fillMaxSize()) {

        if (url.isNotEmpty()) {
            AndroidView(factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            }, update = {
                it.loadUrl(url)
            })
            FloatingActionButton(
                onClick = {
                    newsViewModel.saveArticle(article)
                    localCoroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = context.getString(R.string.article_saved)
                        )
                    }

                },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
            ) {
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
            }
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(128.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        } else {
            Text(
                text = "Error loading the article...",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }
    }

}