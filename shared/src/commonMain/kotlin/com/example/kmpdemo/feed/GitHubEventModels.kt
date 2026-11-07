package com.example.kmpdemo.feed

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** GitHub Events API：https://docs.github.com/en/rest/activity/events#list-public-events */
@Serializable
data class GitHubEventDto(
    val id: String,
    val type: String,
    val actor: GitHubActorDto? = null,
    val repo: GitHubRepoDto? = null,
    val payload: GitHubPayloadDto? = null,
    @SerialName("created_at") val createdAt: String? = null,
)

@Serializable
data class GitHubActorDto(
    val login: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null,
)

@Serializable
data class GitHubRepoDto(
    val name: String? = null,
    @SerialName("html_url") val htmlUrl: String? = null,
)

@Serializable
data class GitHubPayloadDto(
    val action: String? = null,
    val issue: GitHubIssueDto? = null,
    @SerialName("pull_request") val pullRequest: GitHubPullRequestDto? = null,
    val comment: GitHubCommentDto? = null,
)

@Serializable
data class GitHubIssueDto(
    val number: Int? = null,
    val title: String? = null,
    @SerialName("html_url") val htmlUrl: String? = null,
)

@Serializable
data class GitHubPullRequestDto(
    val number: Int? = null,
    val title: String? = null,
    @SerialName("html_url") val htmlUrl: String? = null,
)

@Serializable
data class GitHubCommentDto(
    @SerialName("html_url") val htmlUrl: String? = null,
)

/** UI 行：由 [GitHubEventDto] 映射，供列表展示。 */
data class GitHubEventRow(
    val id: String,
    val type: String,
    val actor: String,
    val repo: String,
    val title: String,
    val link: String?,
    val createdAt: String?,
)
