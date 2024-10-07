package ru.otus.todo.app.todo.repo

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import ru.otus.todo.common.repo.IRepoTodo
import ru.otus.todo.repo.inmemory.TodoRepoInMemory

@TestConfiguration
class RepoInMemoryConfig {
    @Suppress("unused")
    @Bean()
    @Primary
    fun prodRepo(): IRepoTodo =
        TodoRepoInMemory()
}
