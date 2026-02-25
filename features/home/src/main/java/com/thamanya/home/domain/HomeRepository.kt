package com.thamanya.home.domain

import com.thamanya.domain.DataError
import com.thamanya.domain.Result

interface HomeRepository {
    suspend fun getHomePage(): Result<HomeResponse, DataError.Remote>
    suspend fun searchHomePage(query: String): Result<HomeResponse, DataError.Remote>
}