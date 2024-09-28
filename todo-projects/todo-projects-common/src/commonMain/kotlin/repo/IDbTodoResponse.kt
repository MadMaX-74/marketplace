package repo

import models.Todo
import models.TodoError


sealed interface IDbTodoResponse: IDbResponse<Todo>

data class DbTodoResponseOk(
    val data: Todo
): IDbTodoResponse

data class DbTodoResponseErr(
    val errors: List<TodoError> = emptyList()
): IDbTodoResponse {
    constructor(err: TodoError): this(listOf(err))
}

data class DbTodoResponseErrWithData(
    val data: Todo,
    val errors: List<TodoError> = emptyList()
): IDbTodoResponse {
    constructor(todo: Todo, err: TodoError): this(todo, listOf(err))
}
