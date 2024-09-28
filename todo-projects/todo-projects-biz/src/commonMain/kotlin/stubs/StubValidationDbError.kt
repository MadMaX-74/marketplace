package ru.otus.todo.biz.stubs


import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.helpers.fail
import ru.otus.todo.common.models.TodoError
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.common.stubs.TodoStubs
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker


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
