package com.erkindilekci.stockmarketbook.data.mapper

import com.erkindilekci.stockmarketbook.data.local.CompanyEntity
import com.erkindilekci.stockmarketbook.data.remote.dto.CompanyDetailDto
import com.erkindilekci.stockmarketbook.domain.model.Company
import com.erkindilekci.stockmarketbook.domain.model.CompanyDetail

fun CompanyEntity.toCompany(): Company = Company(name, symbol, exchange)

fun Company.toCompanyEntity(): CompanyEntity = CompanyEntity(name, symbol, exchange)

fun CompanyDetailDto.toCompanyInfo(): CompanyDetail =
    CompanyDetail(
        symbol ?: "",
        description ?: "",
        name ?: "",
        country ?: "",
        industry ?: ""
    )
