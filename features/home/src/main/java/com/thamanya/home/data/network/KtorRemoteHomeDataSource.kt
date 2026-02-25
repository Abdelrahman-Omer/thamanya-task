package com.thamanya.home.data.network

import com.thamanya.data.BuildConfig.HOME_SECTION_BASE_URL
import com.thamanya.data.BuildConfig.HOME_SECTION_SEARCH_BASE_URL
import com.thamanya.data.safeCall
import com.thamanya.domain.*
import com.thamanya.home.data.dto.HomeResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post

class KtorRemoteHomeDataSource(
    private val httpClient: HttpClient
) : RemoteHomeDataSource {

    override suspend fun getHomePage(): Result<HomeResponseDto, DataError.Remote> {
        return safeCall<HomeResponseDto> {
            httpClient.get(
                urlString = "${HOME_SECTION_BASE_URL}home_sections"
            )
        }
    }

    override suspend fun homeSearch(query: String): Result<HomeResponseDto, DataError.Remote> {
        return safeCall<HomeResponseDto> {
            httpClient.get(
                urlString = "${HOME_SECTION_SEARCH_BASE_URL}search"
            ) {
                parameter("q", query)
            }
        }
    }

}