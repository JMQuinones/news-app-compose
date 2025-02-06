package com.jmquinones.newsappcompose.ui.components.bottomnavbar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun BottomNavBar(navController: NavHostController, modifier: Modifier = Modifier) {
    val menuItems = listOf(BottomNavBarItem.Home, BottomNavBarItem.Saved, BottomNavBarItem.Search)
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    NavigationBar {
        menuItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    // TODO: Add navigation
                    navController.navigate(item.route)
                },
                label = { Text(text = item.label) },
                icon = {
                    Icon(
                        imageVector = if (index == selectedIndex){
                            item.selectedIcon
                        } else {
                            item.unSelectedIcon
                        },
                        contentDescription = item.label
                    )
                })
        }
    }
}