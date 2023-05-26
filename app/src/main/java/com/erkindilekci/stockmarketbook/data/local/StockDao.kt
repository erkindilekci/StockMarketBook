package com.erkindilekci.stockmarketbook.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface StockDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertCompanyList(companyList: List<CompanyEntity>)

    @Query("DELETE FROM companyentity")
    suspend fun clearCompanyList()

    @Query(
        """
        SELECT * FROM companyentity
        WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
        UPPER(:query) == symbol
    """
    )
    suspend fun searchCompanyList(query: String): List<CompanyEntity>
}
