package org.yechan.mcp.repository

import org.springframework.stereotype.Repository

interface UrlRepository {
    fun save(shortKey: String, originalUrl: String)
    fun findByShortKey(shortKey: String): String?
    fun exists(shortKey: String): Boolean
    fun findAll(): Map<String, String>
    fun delete(shortKey: String): Boolean
}

@Repository
class InMemoryUrlRepository : UrlRepository {
    private val urlMap = mutableMapOf<String, String>()

    override fun save(shortKey: String, originalUrl: String) {
        urlMap[shortKey] = originalUrl
    }

    override fun findByShortKey(shortKey: String): String? {
        return urlMap[shortKey]
    }

    override fun exists(shortKey: String): Boolean {
        return urlMap.containsKey(shortKey)
    }
    
    override fun findAll(): Map<String, String> {
        return urlMap.toMap()
    }
    
    override fun delete(shortKey: String): Boolean {
        return urlMap.remove(shortKey) != null
    }
}
