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

sealed class BottomNavBarItem(val route: String, val selectedIcon: ImageVector, val unSelectedIcon: ImageVector, val label: String) {
    object Home : BottomNavBarItem("home", Icons.Filled.Home, Icons.Outlined.Home, "All News")
    object Search : BottomNavBarItem("search", Icons.Filled.Search, Icons.Outlined.Search, "Search")
    object Saved : BottomNavBarItem("favorite", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder, "Saved")
}