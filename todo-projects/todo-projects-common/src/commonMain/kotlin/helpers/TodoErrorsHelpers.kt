package ru.otus.todo.common.helpers

import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.models.TodoError
import ru.otus.todo.common.models.TodoState

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
inline fun TodoContext.addError(error: TodoError) = errors.add(error)
inline fun TodoContext.addErrors(error: Collection<TodoError>) = errors.addAll(error)

inline fun TodoContext.fail(error: TodoError) {
    addError(error)
    state = TodoState.FAILING
}

inline fun TodoContext.fail(errors: Collection<TodoError>) {
    addErrors(errors)
    state = TodoState.FAILING
}

inline fun errorValidation(
    field: String,
    violationCode: String,
    description: String,
) = TodoError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description"
)
inline fun errorSystem(
    violationCode: String,
    e: Throwable,
) = TodoError(
    code = "system-$violationCode",
    group = "system",
    message = "System error occurred. Our stuff has been informed, please retry later",
    exception = e,
)