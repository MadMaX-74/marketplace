package ru.otus.todo.biz.stubs

import TodoContext
import helpers.fail
import models.TodoError
import models.TodoState
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker
import stubs.TodoStubs


fun ICorChainDsl<TodoContext>.stubValidationBadId(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для идентификатора объявления
    """.trimIndent()
    on { stubCase == TodoStubs.BAD_ID && state == TodoState.RUNNING }
    handle {
        fail(
            TodoError(
                group = "validation",
                code = "validation-id",
                field = "id",
                message = "Wrong id field"
            )
        )
    }
}
