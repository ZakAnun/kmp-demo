package com.example.kmpdemo.feed

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

actual fun createAppHttpClient(): HttpClient =
    HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(defaultGithubJson())
        }
    }
