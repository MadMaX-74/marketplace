package ru.otus.todo.app.todo.config


import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.otus.todo.backend.repository.inmemory.TodoRepoStub
import ru.otus.todo.biz.TodoProcessor
import ru.otus.todo.common.TodoCorSettings
import ru.otus.todo.common.repo.IRepoTodo
import ru.otus.todo.repo.inmemory.TodoRepoInMemory
import ru.otus.todo.repo.postgresql.RepoTodoSql

@Suppress("unused")
@EnableConfigurationProperties(TodoConfigPostgres::class)
@Configuration
class TodoConfig(val postgresConfig: TodoConfigPostgres) {
    @Bean
    fun processor(corSettings: TodoCorSettings) = TodoProcessor(corSettings = corSettings)

    @Bean
    fun testRepo(): IRepoTodo = TodoRepoInMemory()

    @Bean
    fun prodRepo(): IRepoTodo = RepoTodoSql(postgresConfig.psql)


    @Bean
    fun stubRepo(): IRepoTodo = TodoRepoStub()

    @Bean
    fun corSettings(): TodoCorSettings = TodoCorSettings(
        repoTest = testRepo(),
        repoProd = prodRepo(),
        repoStub = stubRepo(),
    )

    @Bean
    fun appSettings(
        corSettings: TodoCorSettings,
        processor: TodoProcessor,
    ) = TodoAppSettings(
        corSettings = corSettings,
        processor = processor,
    )
}
