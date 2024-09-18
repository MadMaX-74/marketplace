package ru.otus.todo.biz.stubs

import TodoContext
import helpers.fail
import models.TodoError
import models.TodoState
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker
import stubs.TodoStubs


fun ICorChainDsl<TodoContext>.stubValidationBadTitle(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки валидации для заголовка
    """.trimIndent()

    on { stubCase == TodoStubs.BAD_TITLE && state == TodoState.RUNNING }
    handle {
        fail(
            TodoError(
                group = "validation",
                code = "validation-title",
                field = "title",
                message = "Wrong title field"
            )
        )
    }
}
