package com.jmquinones.newsappcompose.ui.components.searchnews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.jmquinones.newsappcompose.ui.components.newslist.NewsListPaged
import com.jmquinones.newsappcompose.ui.navigation.SearchNewsScreen
import com.jmquinones.newsappcompose.viewmodel.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchNews(
    modifier: Modifier = Modifier, navController: NavHostController,
    viewModel: NewsViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState
) {
    val query by viewModel.query.collectAsState()
    val searchResults = viewModel.searchResults.collectAsLazyPagingItems()

    Column(modifier = modifier) {
        TextField(
            value = query,
            onValueChange = { viewModel.setQuery(it) },
            label = { Text("Search for news...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        //val searchedNews = viewModel.searchedNewsPageFlow.collectAsLazyPagingItems()
        NewsListPaged(
            navController = navController,
            items = searchResults,
            snackbarHostState = snackbarHostState
        )
        /*if (searchedNews != null){
        } else {
            Text(text = "Null")
        }*/
    }
}