package com.erkindilekci.stockmarketbook.util

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.erkindilekci.stockmarketbook.presentation.detailscreen.DetailScreen
import com.erkindilekci.stockmarketbook.presentation.listscreen.ListScreen

@Composable
fun ComposeNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ListScreen.route) {
        composable(Screen.ListScreen.route) {
            ListScreen(navController = navController)
        }

        composable(Screen.DetailScreen.route + "/{symbol}") {
            DetailScreen()
        }
    }
}
