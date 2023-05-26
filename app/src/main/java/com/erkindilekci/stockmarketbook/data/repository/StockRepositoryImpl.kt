package com.erkindilekci.stockmarketbook.data.repository

import com.erkindilekci.stockmarketbook.data.csv.CsvParser
import com.erkindilekci.stockmarketbook.data.local.StockDatabase
import com.erkindilekci.stockmarketbook.data.mapper.toCompany
import com.erkindilekci.stockmarketbook.data.mapper.toCompanyEntity
import com.erkindilekci.stockmarketbook.data.mapper.toCompanyInfo
import com.erkindilekci.stockmarketbook.data.remote.StockApi
import com.erkindilekci.stockmarketbook.domain.model.Company
import com.erkindilekci.stockmarketbook.domain.model.CompanyDetail
import com.erkindilekci.stockmarketbook.domain.model.IntradayDetail
import com.erkindilekci.stockmarketbook.domain.repository.StockRepository
import com.erkindilekci.stockmarketbook.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListParser: CsvParser<Company>,
    private val intradayDetailParser: CsvParser<IntradayDetail>
) : StockRepository {
    private val dao = db.dao

    override suspend fun getCompanyList(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<Company>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val localList = dao.searchCompanyList(query).map { it.toCompany() }
            emit(Resource.Success(localList))

            val isDatabaseEmpty = localList.isEmpty() && query.isBlank()
            val shouldLoadFromCache = !isDatabaseEmpty && !fetchFromRemote

            if (shouldLoadFromCache) {
                emit(Resource.Loading(isLoading = false))
                return@flow
            }

            val remoteList = try {
                val response = api.getRemoteList()
                companyListParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(e.localizedMessage ?: "Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(e.localizedMessage ?: "Couldn't load data"))
                null
            }

            remoteList?.let { list ->
                dao.clearCompanyList()
                dao.insertCompanyList(
                    list.map { it.toCompanyEntity() }
                )
                emit(Resource.Success(
                    data = dao.searchCompanyList("").map { it.toCompany() }
                ))
                emit(Resource.Loading(isLoading = false))
            }
        }
    }

    override suspend fun getIntradayDetail(symbol: String): Resource<List<IntradayDetail>> {
        return try {
            val response = api.getIntradayDetail(symbol)
            val result = intradayDetailParser.parse(response.byteStream())
            Resource.Success(result)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage ?: "Couldn't load detail")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage ?: "Couldn't load detail")
        }
    }

    override suspend fun getCompanyDetail(symbol: String): Resource<CompanyDetail> {
        return try {
            val result = api.getCompanyDetail(symbol).toCompanyInfo()
            Resource.Success(result)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage ?: "Couldn't load detail")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage ?: "Couldn't load detail")
        }
    }
}
