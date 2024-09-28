package repo


import helpers.errorSystem
import models.TodoError
import models.TodoId
import repo.exceptions.RepoException

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
