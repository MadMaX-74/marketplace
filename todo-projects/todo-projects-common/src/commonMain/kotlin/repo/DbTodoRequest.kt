package ru.otus.todo.common.repo

import ru.otus.todo.common.models.Todo


data class DbTodoRequest(
    val todo: Todo
)
