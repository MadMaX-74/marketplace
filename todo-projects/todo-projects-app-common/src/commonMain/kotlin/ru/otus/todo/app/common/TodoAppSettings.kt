package ru.otus.todo.app.common

import TodoCorSettings
import ru.otus.todo.biz.TodoProcessor

interface TodoAppSettings {
    val processor: TodoProcessor
    val corSettings: TodoCorSettings
}