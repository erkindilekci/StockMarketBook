package com.erkindilekci.stockmarketbook.data.mapper

import com.erkindilekci.stockmarketbook.data.remote.dto.IntradayDetailDto
import com.erkindilekci.stockmarketbook.domain.model.IntradayDetail
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun IntradayDetailDto.toIntradayDetail(): IntradayDetail {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timeString, formatter)

    return IntradayDetail(localDateTime, close)
}
