package com.erkindilekci.stockmarketbook.data.csv

import com.erkindilekci.stockmarketbook.domain.model.Company
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyListParser @Inject constructor() : CsvParser<Company> {
    override suspend fun parse(stream: InputStream): List<Company> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { row ->
                    val symbol = row.getOrNull(0)
                    val name = row.getOrNull(1)
                    val exchange = row.getOrNull(2)
                    Company(
                        name = name ?: return@mapNotNull null,
                        symbol = symbol ?: return@mapNotNull null,
                        exchange = exchange ?: return@mapNotNull null
                    )
                }.also { csvReader.close() }
        }
    }
}
