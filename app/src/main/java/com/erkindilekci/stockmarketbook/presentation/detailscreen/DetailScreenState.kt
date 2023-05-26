package com.erkindilekci.stockmarketbook.presentation.detailscreen

import com.erkindilekci.stockmarketbook.domain.model.CompanyDetail
import com.erkindilekci.stockmarketbook.domain.model.IntradayDetail

data class DetailScreenState(
    val stockDetailList: List<IntradayDetail> = emptyList(),
    val companyDetail: CompanyDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
