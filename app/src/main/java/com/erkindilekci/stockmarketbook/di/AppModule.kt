package com.erkindilekci.stockmarketbook.di

import android.app.Application
import androidx.room.Room
import com.erkindilekci.stockmarketbook.data.local.StockDatabase
import com.erkindilekci.stockmarketbook.data.remote.StockApi
import com.erkindilekci.stockmarketbook.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideStockApi(): StockApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create()

    @Provides
    @Singleton
    fun provideStockDatabase(application: Application): StockDatabase =
        Room.databaseBuilder(
            application,
            StockDatabase::class.java,
            "stock_database"
        ).build()
}
