package com.thamanya.home.data.network

import com.thamanya.domain.DataError
import com.thamanya.domain.Result
import com.thamanya.home.data.dto.HomeResponseDto


interface RemoteHomeDataSource {
    suspend fun getHomePage(): Result<HomeResponseDto, DataError.Remote>
    suspend fun homeSearch(query: String): Result<HomeResponseDto, DataError.Remote>
}