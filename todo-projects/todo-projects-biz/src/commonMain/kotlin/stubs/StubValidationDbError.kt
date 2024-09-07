package ru.otus.todo.biz.stubs

import TodoContext
import helpers.fail
import models.TodoError
import models.TodoState
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker
import stubs.TodoStubs


fun ICorChainDsl<TodoContext>.stubDbError(title: String) = worker {
    this.title = title
    this.description = """
        Кейс ошибки базы данных
    """.trimIndent()
    on { stubCase == TodoStubs.DB_ERROR && state == TodoState.RUNNING }
    handle {
        fail(
            TodoError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}
