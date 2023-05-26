package com.erkindilekci.stockmarketbook.presentation.listscreen

import com.erkindilekci.stockmarketbook.domain.model.Company

data class ListScreenState(
    val companies: List<Company> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val error: String = ""
)
