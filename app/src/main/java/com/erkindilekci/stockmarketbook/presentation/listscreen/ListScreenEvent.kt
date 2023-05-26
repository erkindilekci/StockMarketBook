package com.erkindilekci.stockmarketbook.presentation.listscreen

sealed class ListScreenEvent {
    object Refresh : ListScreenEvent()
    data class OnSearchQueryChange(val query: String) : ListScreenEvent()
}
