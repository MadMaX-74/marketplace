package ru.otus.todo.common.models


data class TodoFilter(
    var searchString: String = "",
    var status: TodoStatus = TodoStatus.NONE
)
