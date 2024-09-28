package ru.otus.todo.biz.validation

import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.helpers.errorValidation
import ru.otus.todo.common.helpers.fail
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker

fun ICorChainDsl<TodoContext>.validateCreatedDateNotEmpty(title: String) = worker {
    this.title = title
    on { false } // Проверка на null
    handle {
        fail(
            errorValidation(
                field = "createdDate",
                violationCode = "empty",
                description = "createdDate must not be null"
            )
        )
    }
}

