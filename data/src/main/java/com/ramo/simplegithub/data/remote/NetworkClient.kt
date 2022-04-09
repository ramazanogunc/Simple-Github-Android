package com.ramo.simplegithub.data.remote

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
            logger = Logger.DEFAULT
            level = LogLevel.BODY
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