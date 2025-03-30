package org.yechan.mcp.config

/* 
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.yechan.mcp.repository.InMemoryUrlRepository
import org.yechan.mcp.repository.PostgresUrlRepository
import org.yechan.mcp.repository.UrlRepository
import org.yechan.mcp.repository.exposed.UrlMappings
import javax.sql.DataSource

@Configuration
class RepositoryConfig {
    private val logger = KotlinLogging.logger {}

    @Bean
    @ConditionalOnProperty(
        name = ["url-shortener.storage-type"], 
        havingValue = "in-memory", 
        matchIfMissing = true
    )
    @Primary
    fun inMemoryUrlRepository(): UrlRepository {
        logger.info { "Using In-Memory URL repository" }
        return InMemoryUrlRepository()
    }

    @Bean
    @ConditionalOnProperty(
        name = ["url-shortener.storage-type"], 
        havingValue = "postgres"
    )
    @Primary
    fun postgresUrlRepository(dataSource: DataSource): UrlRepository {
        logger.info { "Using PostgreSQL URL repository" }
        
        // Connect to the database
        val database = Database.connect(dataSource)
        logger.debug { "Connected to database: ${database.url}" }
        
        // Create tables if they don't exist
        try {
            transaction {
                logger.debug { "Creating tables if they don't exist" }
                SchemaUtils.createMissingTablesAndColumns(UrlMappings)
                logger.debug { "Tables created successfully" }
            }
        } catch (e: Exception) {
            logger.error(e) { "Failed to create tables: ${e.message}" }
            throw e
        }
        
        return PostgresUrlRepository()
    }
}
*/

// 이 파일은 더 이상 사용되지 않습니다.
// Exposed 라이브러리 의존성 문제로 인해 비활성화했습니다.
