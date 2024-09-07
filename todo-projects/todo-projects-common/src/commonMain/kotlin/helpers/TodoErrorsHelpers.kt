package helpers

import TodoContext
import models.TodoError
import models.TodoState

fun Throwable.asTodoError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = TodoError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)
inline fun TodoContext.addError(vararg error: TodoError) = errors.addAll(error)

inline fun TodoContext.fail(error: TodoError) {
    addError(error)
    state = TodoState.FAILING
}