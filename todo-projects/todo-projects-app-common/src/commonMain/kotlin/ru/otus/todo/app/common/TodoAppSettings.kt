package ru.otus.todo.app.common


import ru.otus.todo.biz.TodoProcessor
import ru.otus.todo.common.TodoCorSettings

interface TodoAppSettings {
    val processor: TodoProcessor
    val corSettings: TodoCorSettings
}