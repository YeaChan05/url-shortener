package org.yechan.mcp.repository

/*
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.yechan.mcp.dto.UrlInfoResponse
import org.yechan.mcp.repository.exposed.UrlMappings

class PostgresUrlRepository : UrlRepository {
    private val logger = KotlinLogging.logger {}

    override fun save(shortKey: String, originalUrl: String) {
        logger.info { "Saving URL mapping in PostgreSQL: $shortKey -> $originalUrl" }
        
        transaction {
            UrlMappings.insert {
                it[UrlMappings.shortKey] = shortKey
                it[UrlMappings.originalUrl] = originalUrl
                it[createdAt] = System.currentTimeMillis()
            }
        }
    }

    override fun findByShortKey(shortKey: String): String? {
        logger.info { "Finding URL in PostgreSQL by short key: $shortKey" }
        
        return transaction {
            UrlMappings.select { UrlMappings.shortKey eq shortKey }
                .map { it[UrlMappings.originalUrl] }
                .singleOrNull()
        }
    }

    override fun exists(shortKey: String): Boolean {
        logger.info { "Checking if short key exists in PostgreSQL: $shortKey" }
        
        return transaction {
            UrlMappings.select { UrlMappings.shortKey eq shortKey }
                .count() > 0
        }
    }
    
    override fun findAll(): List<UrlInfoResponse> {
        logger.info { "Retrieving all URL mappings from PostgreSQL" }
        
        return transaction {
            UrlMappings.selectAll()
                .map { 
                    UrlInfoResponse(
                        shortKey = it[UrlMappings.shortKey],
                        originalUrl = it[UrlMappings.originalUrl],
                        createdAt = it[UrlMappings.createdAt]
                    )
                }
        }
    }
    
    override fun delete(shortKey: String): Boolean {
        logger.info { "Deleting URL mapping from PostgreSQL: $shortKey" }
        
        return transaction {
            val deletedCount = UrlMappings.deleteWhere { UrlMappings.shortKey eq shortKey }
            deletedCount > 0
        }
    }
}
*/

// 이 파일은 더 이상 사용되지 않습니다.
// Exposed 라이브러리 의존성 문제로 인해 비활성화했습니다.
