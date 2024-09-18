package ru.otus.todo.app.todo.config

import org.springdoc.core.configuration.SpringDocConfiguration
import org.springdoc.core.properties.SpringDocConfigProperties
import org.springdoc.core.providers.ObjectMapperProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

// swagger url: http://localhost:8080/swagger-ui/index.html
@Configuration
class SwaggerConfiguration {
    @Bean
    @Primary
    fun springDocConfiguration() = SpringDocConfiguration()

    @Bean
    fun springDocConfigProperties() = SpringDocConfigProperties()

    @Bean
    fun objectMapperProvider(springDocConfigProperties: SpringDocConfigProperties) =
        ObjectMapperProvider(springDocConfigProperties)
}
