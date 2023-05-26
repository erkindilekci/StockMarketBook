package com.erkindilekci.stockmarketbook.util

sealed class Screen(val route: String) {
    object ListScreen : Screen("list_screen")
    object DetailScreen : Screen("detail_screen")
}
