package com.thamanya.domain.exceptions

import com.thamanya.domain.Error

/**
 * Standardized network errors to map HTTP responses or connection issues
 * to domain-specific failures.
 */
sealed class NetworkError : Error {
    object Unauthorized : NetworkError() // 401
    object Forbidden : NetworkError() // 403
    object NotFound : NetworkError() // 404
    object RequestTimeout : NetworkError() // 408
    object PayloadTooLarge : NetworkError() // 413
    object ServerError : NetworkError() // 5xx
    object Unknown : NetworkError() // Mapped generic error
    object NoConnection : NetworkError() // IOException / SocketException
}
