package org.yechan.mcp.admin.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.yechan.mcp.admin.model.ShortenRequest
import org.yechan.mcp.admin.model.ShortenResponse
import org.yechan.mcp.admin.model.UrlInfo

class ApiClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
    
    private val baseUrl = "http://localhost:8080/api"
    
    suspend fun shortenUrl(url: String): ShortenResponse {
        return client.post("$baseUrl/shorten") {
            contentType(ContentType.Application.Json)
            setBody(ShortenRequest(url))
        }.body()
    }
    
    suspend fun getAllUrls(): List<UrlInfo> {
        return client.get("$baseUrl/shorten/urls").body()
    }
    
    suspend fun deleteUrl(shortKey: String) {
        client.delete("$baseUrl/shorten/$shortKey")
    }
    
    suspend fun getOriginalUrl(shortKey: String): String {
        val response: Map<String, String> = client.get("$baseUrl/original/$shortKey").body()
        return response["originalUrl"] ?: throw IllegalStateException("Original URL not found")
    }
}
