package com.example.kmpdemo.feed

fun GitHubEventDto.toRow(): GitHubEventRow {
    val actor = actor?.login ?: "unknown"
    val repoName = repo?.name ?: "—"
    val title = payloadTitle()
    val link = primaryLink()
    return GitHubEventRow(
        id = id,
        type = type,
        actor = actor,
        repo = repoName,
        title = title,
        link = link,
        createdAt = createdAt,
    )
}

private fun GitHubEventDto.payloadTitle(): String {
    val p = payload ?: return ""
    return when {
        p.pullRequest != null ->
            listOfNotNull(p.pullRequest?.title, p.pullRequest?.number?.let { "#$it" })
                .joinToString(" ")
        p.issue != null ->
            listOfNotNull(p.issue?.title, p.issue?.number?.let { "#$it" })
                .joinToString(" ")
        p.action != null -> p.action!!
        else -> ""
    }.trim()
}

private fun GitHubEventDto.primaryLink(): String? {
    val p = payload
    return when {
        p?.pullRequest?.htmlUrl != null -> p.pullRequest!!.htmlUrl
        p?.issue?.htmlUrl != null -> p.issue!!.htmlUrl
        p?.comment?.htmlUrl != null -> p.comment!!.htmlUrl
        repo?.htmlUrl != null -> repo!!.htmlUrl
        else -> null
    }
}
