package ru.otus.todo.biz.stubs


import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.helpers.fail
import ru.otus.todo.common.models.TodoError
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.common.stubs.TodoStubs
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker


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
