package com.erkindilekci.stockmarketbook.di

import com.erkindilekci.stockmarketbook.data.csv.CompanyListParser
import com.erkindilekci.stockmarketbook.data.csv.CsvParser
import com.erkindilekci.stockmarketbook.data.csv.IntradayListParser
import com.erkindilekci.stockmarketbook.data.repository.StockRepositoryImpl
import com.erkindilekci.stockmarketbook.domain.model.Company
import com.erkindilekci.stockmarketbook.domain.model.IntradayDetail
import com.erkindilekci.stockmarketbook.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCompanyParser(
        companyListParser: CompanyListParser
    ): CsvParser<Company>

    @Binds
    @Singleton
    abstract fun bindIntradayParser(
        intradayListParser: IntradayListParser
    ): CsvParser<IntradayDetail>

    @Binds
    @Singleton
    abstract fun bindRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}
