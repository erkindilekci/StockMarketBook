package com.erkindilekci.stockmarketbook.data.csv

import com.erkindilekci.stockmarketbook.data.mapper.toIntradayDetail
import com.erkindilekci.stockmarketbook.data.remote.dto.IntradayDetailDto
import com.erkindilekci.stockmarketbook.domain.model.IntradayDetail
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayListParser @Inject constructor() : CsvParser<IntradayDetail> {
    override suspend fun parse(stream: InputStream): List<IntradayDetail> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { row ->
                    val timestamp = row.getOrNull(0) ?: return@mapNotNull null
                    val close = row.getOrNull(4) ?: return@mapNotNull null
                    IntradayDetailDto(
                        timeString = timestamp,
                        close = close.toDouble()
                    ).toIntradayDetail()
                }
                .filter { it.date.dayOfMonth == LocalDateTime.now().minusDays(1).dayOfMonth }
                .sortedBy { it.date.hour }
                .also { csvReader.close() }
        }
    }
}
