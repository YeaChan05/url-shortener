package org.yechan.mcp.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.yechan.mcp.repository.UrlRepository
import org.yechan.mcp.dto.UrlInfoResponse
import java.time.Instant
import kotlin.random.Random

@Service
class UrlShortenerService(private val urlRepository: UrlRepository) {
    
    private val logger = KotlinLogging.logger {}
    
    // 생성 시간을 저장하기 위한 맵
    private val creationTimes = mutableMapOf<String, Long>()
    
    fun shortenUrl(originalUrl: String): String {
        logger.info { "Shortening URL: $originalUrl" }
        
        var shortKey = generateShortKey()
        while (urlRepository.exists(shortKey)) {
            shortKey = generateShortKey()
        }
        
        urlRepository.save(shortKey, originalUrl)
        creationTimes[shortKey] = Instant.now().toEpochMilli()
        logger.info { "URL shortened. Short key: $shortKey" }
        
        return shortKey
    }
    
    fun getOriginalUrl(shortKey: String): String? {
        logger.info { "Retrieving original URL for short key: $shortKey" }
        
        val originalUrl = urlRepository.findByShortKey(shortKey)
        if (originalUrl != null) {
            logger.info { "Original URL found: $originalUrl" }
        } else {
            logger.info { "No URL found for short key: $shortKey" }
        }
        
        return originalUrl
    }
    
    fun getAllUrls(): List<UrlInfoResponse> {
        logger.info { "Retrieving all shortened URLs" }
        
        return urlRepository.findAll().map { (shortKey, originalUrl) ->
            UrlInfoResponse(
                shortKey = shortKey,
                originalUrl = originalUrl,
                createdAt = creationTimes[shortKey] ?: Instant.now().toEpochMilli()
            )
        }
    }
    
    fun deleteUrl(shortKey: String): Boolean {
        logger.info { "Deleting URL with short key: $shortKey" }
        
        if (!urlRepository.exists(shortKey)) {
            logger.info { "URL with short key $shortKey not found" }
            return false
        }
        
        val success = urlRepository.delete(shortKey)
        
        if (success) {
            creationTimes.remove(shortKey)
            logger.info { "URL with short key $shortKey deleted successfully" }
        } else {
            logger.error { "Failed to delete URL with short key $shortKey" }
        }
        
        return success
    }
    
    private fun generateShortKey(): String {
        val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val firstChar = ('a'..'z') + ('A'..'Z')
        
        return firstChar.random() + (1..5)
            .map { Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }
}
