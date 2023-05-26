package com.erkindilekci.stockmarketbook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CompanyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class StockDatabase : RoomDatabase() {
    abstract val dao: StockDao
}
