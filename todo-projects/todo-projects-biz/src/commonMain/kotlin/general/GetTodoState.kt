package ru.otus.todo.biz.general


import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker

fun ICorChainDsl<TodoContext>.getTodoState(title: String) = worker {
    this.title = title
    this.description = """
        Получаем состояние из сервиса состояний
    """.trimIndent()
    on { state == TodoState.RUNNING }
    handle {
        corSettings
    }
}
