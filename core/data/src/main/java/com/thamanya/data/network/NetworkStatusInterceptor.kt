package com.thamanya.data.network

import com.thamanya.domain.exceptions.NetworkError
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Global OkHttp Interceptor that traps known HTTP status codes and throws custom Exceptions
 * for seamless mapping to domain-level Results.
 */
class NetworkStatusInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = try {
            chain.proceed(request)
        } catch (e: IOException) {
            throw DomainNetworkException(NetworkError.NoConnection, "Network connection issue: ${e.message}")
        }

        if (!response.isSuccessful) {
            val errorType = when (response.code) {
                401 -> NetworkError.Unauthorized
                403 -> NetworkError.Forbidden
                404 -> NetworkError.NotFound
                408 -> NetworkError.RequestTimeout
                413 -> NetworkError.PayloadTooLarge
                in 500..599 -> NetworkError.ServerError
                else -> NetworkError.Unknown
            }
            throw DomainNetworkException(errorType, "HTTP Error ${response.code}: ${response.message}")
        }
        return response
    }
}

class DomainNetworkException(val errorType: NetworkError, message: String) : IOException(message)
