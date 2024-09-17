package ru.otus.todo.app.todo.config

import TodoCorSettings
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.otus.todo.biz.TodoProcessor

@Suppress("unused")
@Configuration
class TodoConfig {
    @Bean
    fun processor(corSettings: TodoCorSettings) = TodoProcessor(corSettings = corSettings)

    @Bean
    fun corSettings(): TodoCorSettings = TodoCorSettings()

    @Bean
    fun appSettings(
        corSettings: TodoCorSettings,
        processor: TodoProcessor,
    ) = TodoAppSettings(
        corSettings = corSettings,
        processor = processor,
    )
}
