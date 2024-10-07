package ru.otus.todo.biz.repo

import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker


fun ICorChainDsl<TodoContext>.repoPrepareDelete(title: String) = worker {
    this.title = title
    description = """
        Готовим данные к удалению из БД
    """.trimIndent()
    on { state == TodoState.RUNNING }
    handle {
        todoRepoPrepare = todoValidated.deepCopy()
    }
}
