package repo

import models.Todo
import models.TodoError


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
