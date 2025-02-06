package com.jmquinones.newsappcompose.ui.components.bottomnavbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.jmquinones.newsappcompose.ui.navigation.AllNewsScreen
import com.jmquinones.newsappcompose.ui.navigation.SavedNewsScreen
import com.jmquinones.newsappcompose.ui.navigation.SearchNewsScreen

sealed class BottomNavBarItem(
    val route: Any,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val label: String
) {
    object Home : BottomNavBarItem(AllNewsScreen, Icons.Filled.Home, Icons.Outlined.Home, "All News")
    object Search : BottomNavBarItem(SearchNewsScreen, Icons.Filled.Search, Icons.Outlined.Search, "Search")
    object Saved :
        BottomNavBarItem(SavedNewsScreen, Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder, "Saved")
}