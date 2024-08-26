package ru.otus.todo.app.todo.config

import TodoCorSettings
import ru.otus.todo.app.common.TodoAppSettings
import ru.otus.todo.biz.TodoProcessor

data class TodoAppSettings(
    override val corSettings: TodoCorSettings,
    override val processor: TodoProcessor,
): TodoAppSettings
