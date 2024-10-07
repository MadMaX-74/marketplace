package ru.otus.todo.app.todo.config


import ru.otus.todo.app.common.TodoAppSettings
import ru.otus.todo.biz.TodoProcessor
import ru.otus.todo.common.TodoCorSettings

data class TodoAppSettings(
    override val corSettings: TodoCorSettings,
    override val processor: TodoProcessor,
): TodoAppSettings
