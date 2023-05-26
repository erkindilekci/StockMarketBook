package com.erkindilekci.stockmarketbook.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyEntity(
    val name: String,
    val symbol: String,
    val exchange: String,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)
