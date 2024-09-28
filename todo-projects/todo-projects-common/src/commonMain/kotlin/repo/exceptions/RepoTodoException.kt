package repo.exceptions


import models.TodoId

open class RepoTodoException(
    @Suppress("unused")
    val todoId: TodoId,
    msg: String,
): RepoException(msg)
