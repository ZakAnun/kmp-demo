package com.example.kmpdemo.feed

import kotlin.test.Test
import kotlin.test.assertEquals

class GitHubEventMapperTest {
    @Test
    fun mapsIssueEvent() {
        val dto = GitHubEventDto(
            id = "1",
            type = "IssuesEvent",
            actor = GitHubActorDto(login = "octocat"),
            repo = GitHubRepoDto(name = "octo/repo", htmlUrl = "https://github.com/octo/repo"),
            payload = GitHubPayloadDto(
                action = "opened",
                issue = GitHubIssueDto(number = 42, title = "Fix bug", htmlUrl = "https://github.com/octo/repo/issues/42"),
            ),
            createdAt = "2024-01-02T10:00:00Z",
        )
        val row = dto.toRow()
        assertEquals("IssuesEvent", row.type)
        assertEquals("octocat", row.actor)
        assertEquals("octo/repo", row.repo)
        assertEquals("Fix bug #42", row.title)
        assertEquals("https://github.com/octo/repo/issues/42", row.link)
    }
}
