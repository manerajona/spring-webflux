package com.github.manerajona.reactive.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.io.support.ResourcePatternResolver
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import java.io.IOException

@Profile("h2")
@Configuration
class MigrationH2Config(val resolver: ResourcePatternResolver) {

    @Value("\${db.migration.h2.path}")
    private lateinit var resourcesPath: String

    @Bean
    @Throws(IOException::class)
    protected fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
        val resources = resolver.getResources(resourcesPath)
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        initializer.setDatabasePopulator(ResourceDatabasePopulator(*resources))
        return initializer
    }
}
