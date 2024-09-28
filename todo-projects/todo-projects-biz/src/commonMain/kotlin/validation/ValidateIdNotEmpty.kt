package ru.otus.todo.biz.validation

import TodoContext
import helpers.errorValidation
import helpers.fail
import models.TodoId
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker


fun ICorChainDsl<TodoContext>.validateIdNotEmpty(title: String) = worker {
    this.title = title
    on { todoValidating.id == TodoId.NONE }
    handle {
        fail(
            errorValidation(
                field = "id",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
