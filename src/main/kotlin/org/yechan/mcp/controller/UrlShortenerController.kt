package org.yechan.mcp.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.yechan.mcp.dto.ShortenRequest
import org.yechan.mcp.dto.ShortenResponse
import org.yechan.mcp.dto.UrlInfoResponse
import org.yechan.mcp.service.UrlShortenerService
import java.net.URI
import jakarta.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api")
class UrlShortenerController(private val urlShortenerService: UrlShortenerService) {
    
    private val logger = KotlinLogging.logger {}
    
    @PostMapping("/shorten")
    fun shortenUrl(@RequestBody request: ShortenRequest, servletRequest: HttpServletRequest): ResponseEntity<ShortenResponse> {
        logger.info { "Received request to shorten URL: ${request.url}" }
        
        val shortKey = urlShortenerService.shortenUrl(request.url)
        val baseUrl = getBaseUrl(servletRequest)
        val shortenedUrl = "$baseUrl/api/shorten/$shortKey"
        
        logger.info { "Shortened URL created: $shortenedUrl" }
        
        return ResponseEntity.ok(ShortenResponse(shortenedUrl, shortKey))
    }
    
    @GetMapping("/shorten/{shortKey}")
    fun redirectToOriginalUrl(@PathVariable shortKey: String): ResponseEntity<Any> {
        logger.info { "Received request to resolve short key: $shortKey" }
        
        val originalUrl = urlShortenerService.getOriginalUrl(shortKey)
        
        return if (originalUrl != null) {
            logger.info { "Redirecting to original URL: $originalUrl" }
            ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build()
        } else {
            logger.info { "Short key not found: $shortKey" }
            ResponseEntity.notFound().build()
        }
    }
    
    @GetMapping("/original/{shortKey}")
    fun getOriginalUrl(@PathVariable shortKey: String): ResponseEntity<Map<String, String>> {
        logger.info { "Received request to get original URL for short key: $shortKey" }
        
        val originalUrl = urlShortenerService.getOriginalUrl(shortKey)
        
        return if (originalUrl != null) {
            logger.info { "Original URL found for key $shortKey: $originalUrl" }
            ResponseEntity.ok(mapOf("originalUrl" to originalUrl))
        } else {
            logger.info { "Short key not found: $shortKey" }
            ResponseEntity.notFound().build()
        }
    }
    
    @GetMapping("/shorten/urls")
    fun getAllUrls(): ResponseEntity<List<UrlInfoResponse>> {
        logger.info { "Received request to get all shortened URLs" }
        
        val urls = urlShortenerService.getAllUrls()
        
        return ResponseEntity.ok(urls)
    }
    
    @DeleteMapping("/shorten/{shortKey}")
    fun deleteUrl(@PathVariable shortKey: String): ResponseEntity<Any> {
        logger.info { "Received request to delete URL with short key: $shortKey" }
        
        val success = urlShortenerService.deleteUrl(shortKey)
        
        return if (success) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    private fun getBaseUrl(request: HttpServletRequest): String {
        val scheme = request.scheme
        val serverName = request.serverName
        val serverPort = request.serverPort
        val contextPath = request.contextPath
        
        val portString = if ((scheme == "http" && serverPort == 80) || (scheme == "https" && serverPort == 443)) {
            ""
        } else {
            ":$serverPort"
        }
        
        return "$scheme://$serverName$portString$contextPath"
    }
}
