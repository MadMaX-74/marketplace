package ru.otus.todo.common.repo

import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.models.TodoId

data class DbTodoIdRequest(
    val id: TodoId,
) {
    constructor(todo: Todo): this(todo.id)
}
