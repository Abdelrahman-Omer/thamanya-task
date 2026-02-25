package com.thamanya.home.data.repository

import com.thamanya.domain.*
import com.thamanya.home.data.mapper.toHomeResponse
import com.thamanya.home.data.network.RemoteHomeDataSource
import com.thamanya.home.domain.HomeRepository
import com.thamanya.home.domain.HomeResponse

class HomeRepositoryImpl(
    private val dataSource: RemoteHomeDataSource
) : HomeRepository {
    override suspend fun getHomePage(): Result<HomeResponse, DataError.Remote> {
        return dataSource.getHomePage().map { dto -> dto.toHomeResponse() }
    }

    override suspend fun searchHomePage(query: String): Result<HomeResponse, DataError.Remote> {
        return dataSource.homeSearch(query = query).map { dto -> dto.toHomeResponse() }
    }

}
