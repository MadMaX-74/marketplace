package ru.otus.todo.common.repo


import ru.otus.todo.common.helpers.errorSystem
import ru.otus.todo.common.models.TodoError
import ru.otus.todo.common.models.TodoId
import ru.otus.todo.common.repo.exceptions.RepoException

const val ERROR_GROUP_REPO = "repo"

fun errorNotFound(id: TodoId) = DbTodoResponseErr(
    TodoError(
        code = "$ERROR_GROUP_REPO-not-found",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Object with ID: ${id.asString()} is not Found",
    )
)

val errorEmptyId = DbTodoResponseErr(
    TodoError(
        code = "$ERROR_GROUP_REPO-empty-id",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Id must not be null or blank"
    )
)


fun errorDb(e: RepoException) = DbTodoResponseErr(
    errorSystem(
        e = e,
        violationCode = "dbLockEmpty",
    )
)
