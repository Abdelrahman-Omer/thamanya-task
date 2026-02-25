package com.thamanya.data

import com.thamanya.data.dataStore.AppDataStore
import com.thamanya.data.dataStore.PreferencesDataStore
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(engine: HttpClientEngine, dataStore: PreferencesDataStore): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true  // Enable pretty printing in serialization
                    }
                )
            }
            install(HttpTimeout) {
                socketTimeoutMillis = 5_000L
                requestTimeoutMillis = 5_000L
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        android.util.Log.d("KtorClient", message)
                    }
                }
                level = LogLevel.ALL
            }
            defaultRequest {
                contentType(ContentType.Application.Json)

                val token = runBlocking {
                    withContext(Dispatchers.IO) {
                        dataStore.getString(AppDataStore.ACCESS_TOKEN, "")
                    }
                }

                if (token.isNotEmpty()) {
                    this.headers.append("Authorization", "Bearer $token")
                }
            }
        }
    }
}