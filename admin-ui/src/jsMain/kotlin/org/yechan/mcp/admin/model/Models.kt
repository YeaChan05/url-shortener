package org.yechan.mcp.admin.model

import kotlinx.serialization.Serializable

@Serializable
data class ShortenRequest(val url: String)

@Serializable
data class ShortenResponse(
    val shortenedUrl: String,
    val shortKey: String
)

@Serializable
data class UrlInfo(
    val shortKey: String,
    val originalUrl: String,
    val createdAt: Long,
    val formattedCreatedAt: String
)
