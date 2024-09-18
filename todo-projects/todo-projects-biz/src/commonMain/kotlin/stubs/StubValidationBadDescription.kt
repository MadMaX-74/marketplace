package ru.otus.todo.biz.stubs

import TodoContext
import helpers.fail
import models.TodoError
import models.TodoState
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker
import stubs.TodoStubs


fun ICorChainDsl<TodoContext>.stubValidationBadDescription(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для описания задачи
    """.trimIndent()
    on { stubCase == TodoStubs.BAD_DESCRIPTION && state == TodoState.RUNNING }
    handle {
        fail(
            TodoError(
                group = "validation",
                code = "validation-description",
                field = "description",
                message = "Wrong description field"
            )
        )
    }
}
