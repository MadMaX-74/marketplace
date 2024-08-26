package ru.otus.otuskotlin.marketplace.common.helpers

import models.TodoError

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
