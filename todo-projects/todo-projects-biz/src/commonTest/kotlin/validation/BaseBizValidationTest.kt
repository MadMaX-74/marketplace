package ru.otus.todo.biz.validation

import TodoCorSettings
import models.TodoCommand
import ru.otus.todo.biz.TodoProcessor

abstract class BaseBizValidationTest {
    protected abstract val command: TodoCommand
    private val settings by lazy { TodoCorSettings() }
    protected val processor by lazy { TodoProcessor(settings) }
}
