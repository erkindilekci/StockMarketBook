package com.erkindilekci.stockmarketbook.presentation.detailscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erkindilekci.stockmarketbook.domain.repository.StockRepository
import com.erkindilekci.stockmarketbook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: StockRepository
) : ViewModel() {

    var state by mutableStateOf(DetailScreenState())
        private set

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            state = state.copy(isLoading = true)

            val companyDetailResult = async { repository.getCompanyDetail(symbol) }
            val intradayDetailResult = async { repository.getIntradayDetail(symbol) }

            when (val result = companyDetailResult.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        companyDetail = result.data,
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message ?: "Unknown error occurred!",
                        companyDetail = null
                    )
                }

                else -> Unit
            }

            when (val result = intradayDetailResult.await()) {
                is Resource.Success -> {
                    state = state.copy(
                        stockDetailList = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = result.message ?: "Unknown error occurred!",
                        companyDetail = null
                    )
                }

                else -> Unit
            }
        }
    }
}
