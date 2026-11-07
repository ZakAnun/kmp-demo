package com.example.kmpdemo.feed

import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json

internal fun defaultGithubJson(): Json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    explicitNulls = false
}

/** Ktor [HttpClient]：common 声明，android/ios 选择引擎并挂上 JSON 协商。 */
expect fun createAppHttpClient(): HttpClient
