package com.erkindilekci.stockmarketbook.domain.model

import java.time.LocalDateTime

data class IntradayDetail(
    val date: LocalDateTime,
    val close: Double
)
