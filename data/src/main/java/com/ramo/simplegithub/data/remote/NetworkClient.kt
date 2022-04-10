package com.ramo.simplegithub.data.remote

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.gson.*


object NetworkClient {

    const val BASE_URL = "https://api.github.com/"

    val client = HttpClient(CIO) {
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("KTOR", message)
                }
            }
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            gson()
        }

        defaultRequest {
            url(BASE_URL)
            headersOf("application", "vnd.github.v3+json")
        }

    }

}