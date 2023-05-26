package com.erkindilekci.stockmarketbook.presentation.listscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erkindilekci.stockmarketbook.domain.repository.StockRepository
import com.erkindilekci.stockmarketbook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    var state by mutableStateOf(ListScreenState())
        private set

    private var searchJob: Job? = null

    init {
        getCompanyList()
    }

    fun onEvent(event: ListScreenEvent) {
        when (event) {
            is ListScreenEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(timeMillis = 500L)
                    getCompanyList()
                }
            }

            ListScreenEvent.Refresh -> {
                getCompanyList(fetchFromRemote = true)
            }
        }
    }

    private fun getCompanyList(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            repository.getCompanyList(fetchFromRemote, query).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        state = state.copy(error = resource.message ?: "Unknown error occurred!")
                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = resource.isLoading)
                    }

                    is Resource.Success -> {
                        resource.data?.let { companies ->
                            state = state.copy(companies = companies)
                        }
                    }
                }
            }
        }
    }
}
