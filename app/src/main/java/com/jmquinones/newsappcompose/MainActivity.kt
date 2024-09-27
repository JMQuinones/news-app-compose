package com.jmquinones.newsappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jmquinones.newsappcompose.data.models.Article
import com.jmquinones.newsappcompose.data.models.Source
import com.jmquinones.newsappcompose.ui.components.newsdetailwebview.NewsDetailWebView
import com.jmquinones.newsappcompose.ui.components.bottomnavbar.BottomNavBar
import com.jmquinones.newsappcompose.ui.components.breakingnews.BreakingNews
import com.jmquinones.newsappcompose.ui.components.savedarticles.SavedArticles
import com.jmquinones.newsappcompose.ui.navigation.AllNewsScreen
import com.jmquinones.newsappcompose.ui.navigation.ArticleNavType
import com.jmquinones.newsappcompose.ui.navigation.NewsDetail
import com.jmquinones.newsappcompose.ui.navigation.SavedNewsScreen
import com.jmquinones.newsappcompose.ui.navigation.SearchNewsScreen
import com.jmquinones.newsappcompose.ui.theme.NewsAppComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppComposeTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    MainScreen(navController = navController)
}

@Composable
fun MainScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = { BottomNavBar(navController) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AllNewsScreen,
        ) {
            composable<AllNewsScreen> {
                BreakingNews(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding),
                    snackbarHostState = snackbarHostState
                )
            }
            composable<SavedNewsScreen> {
                SavedArticles(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding),
                )
            }
            composable<SearchNewsScreen> { Text(text = "Search News") }
            composable<NewsDetail>(
                typeMap = mapOf(
                    typeOf<Article>() to ArticleNavType.ArticleType,
                    typeOf<Source>() to ArticleNavType.SourceType
                )


            ) { article ->
                val args = article.toRoute<NewsDetail>()
                NewsDetailWebView(
                    article = args.article,
                    navController = navController,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

/*@Preview
@Composable
private fun PreviewApp() {
    NewsAppComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainScreen()
        }
    }
}*/
