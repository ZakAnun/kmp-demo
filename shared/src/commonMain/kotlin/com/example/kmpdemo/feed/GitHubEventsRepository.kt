package com.example.kmpdemo.feed

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter

interface GitHubEventsRepository {
    suspend fun fetchPublicFeed(perPage: Int = 30): List<GitHubEventRow>
}

fun createGitHubEventsRepository(
    httpClient: HttpClient,
): GitHubEventsRepository = GitHubEventsRepositoryImpl(httpClient)

private class GitHubEventsRepositoryImpl(
    private val httpClient: HttpClient,
) : GitHubEventsRepository {

    override suspend fun fetchPublicFeed(perPage: Int): List<GitHubEventRow> {
        val events: List<GitHubEventDto> = httpClient.get("https://api.github.com/events") {
            header("Accept", "application/vnd.github+json")
            header("X-GitHub-Api-Version", "2022-11-28")
            parameter("per_page", perPage.coerceIn(1, 100))
        }.body()
        return events.map { it.toRow() }
    }
}
