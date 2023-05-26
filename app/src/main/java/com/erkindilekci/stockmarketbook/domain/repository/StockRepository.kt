package com.erkindilekci.stockmarketbook.domain.repository

import com.erkindilekci.stockmarketbook.domain.model.Company
import com.erkindilekci.stockmarketbook.domain.model.CompanyDetail
import com.erkindilekci.stockmarketbook.domain.model.IntradayDetail
import com.erkindilekci.stockmarketbook.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyList(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<Company>>>

    suspend fun getIntradayDetail(
        symbol: String
    ): Resource<List<IntradayDetail>>

    suspend fun getCompanyDetail(
        symbol: String
    ): Resource<CompanyDetail>
}
