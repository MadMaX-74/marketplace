package ru.otus.todo.common.repo



data class DbTodoListRequest(
    val titleFilter: String = "",
)
