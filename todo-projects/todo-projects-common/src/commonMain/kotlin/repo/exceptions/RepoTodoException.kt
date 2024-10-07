package ru.otus.todo.common.repo.exceptions

import ru.otus.todo.common.models.TodoId


open class RepoTodoException(
    @Suppress("unused")
    val todoId: TodoId,
    msg: String,
): RepoException(msg)
