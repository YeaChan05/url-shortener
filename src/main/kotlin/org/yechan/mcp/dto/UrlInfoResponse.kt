package org.yechan.mcp.dto

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

data class UrlInfoResponse(
    val shortKey: String,
    val originalUrl: String,
    val createdAt: Long
) {
    val formattedCreatedAt: String
        get() = LocalDateTime.ofInstant(Instant.ofEpochMilli(createdAt), ZoneId.systemDefault())
            .toString()
            .replace("T", " ")
}
