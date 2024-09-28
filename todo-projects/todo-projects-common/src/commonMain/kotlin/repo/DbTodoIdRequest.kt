package repo

import models.Todo
import models.TodoId


data class DbTodoIdRequest(
    val id: TodoId,
) {
    constructor(todo: Todo): this(todo.id)
}
