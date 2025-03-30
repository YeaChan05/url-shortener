package org.yechan.mcp.repository.exposed

/*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Repository
import org.yechan.mcp.repository.UrlRepository
import java.time.LocalDateTime

@Repository
@ConditionalOnProperty(name = ["url-shortener.storage-type"], havingValue = "postgres")
class ExposedUrlRepository : UrlRepository {

    override fun save(shortKey: String, originalUrl: String) {
        transaction {
            UrlMappings.insert {
                it[this.shortKey] = shortKey
                it[this.originalUrl] = originalUrl
                it[createdAt] = LocalDateTime.now()
            }
        }
    }

    override fun findByShortKey(shortKey: String): String? {
        return transaction {
            UrlMappings.select { UrlMappings.shortKey eq shortKey }
                .map { it[UrlMappings.originalUrl] }
                .singleOrNull()
        }
    }

    override fun exists(shortKey: String): Boolean {
        return transaction {
            UrlMappings.select { UrlMappings.shortKey eq shortKey }
                .count() > 0
        }
    }

    override fun findAll(): Map<String, String> {
        return transaction {
            UrlMappings.selectAll()
                .map { it[UrlMappings.shortKey] to it[UrlMappings.originalUrl] }
                .toMap()
        }
    }
    
    fun incrementClickCount(shortKey: String) {
        transaction {
            UrlMappings.update({ UrlMappings.shortKey eq shortKey }) {
                with(SqlExpressionBuilder) {
                    it.update(UrlMappings.clickCount, UrlMappings.clickCount + 1)
                }
            }
        }
    }
    
    fun getClickCount(shortKey: String): Int {
        return transaction {
            UrlMappings.select { UrlMappings.shortKey eq shortKey }
                .map { it[UrlMappings.clickCount] }
                .singleOrNull() ?: 0
        }
    }
}
*/

// 이 파일은 더 이상 사용되지 않습니다.
// 프로젝트를 단순화하기 위해 내용을 주석 처리했습니다.
