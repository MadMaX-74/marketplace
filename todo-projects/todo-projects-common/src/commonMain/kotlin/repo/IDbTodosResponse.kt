package ru.otus.todo.common.repo

import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.models.TodoError


sealed interface IDbTodosResponse: IDbResponse<List<Todo>>

data class DbTodosResponseOk(
    val data: List<Todo>
): IDbTodosResponse

@Suppress("unused")
data class DbTodosResponseErr(
    val errors: List<TodoError> = emptyList()
): IDbTodosResponse {
    constructor(err: TodoError): this(listOf(err))
}
