package ru.otus.todo.biz.stubs


import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.helpers.fail
import ru.otus.todo.common.models.TodoError
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.common.stubs.TodoStubs
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker


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
