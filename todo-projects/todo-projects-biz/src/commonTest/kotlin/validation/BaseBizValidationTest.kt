package ru.otus.todo.biz.validation

import ru.otus.todo.biz.TodoProcessor
import ru.otus.todo.common.TodoCorSettings
import ru.otus.todo.common.models.TodoCommand

abstract class BaseBizValidationTest {
    protected abstract val command: TodoCommand
    private val settings by lazy { TodoCorSettings() }
    protected val processor by lazy { TodoProcessor(settings) }
}
