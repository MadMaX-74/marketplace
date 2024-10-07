package ru.otus.todo.biz.general


import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker


fun ICorChainDsl<TodoContext>.initStatus(title: String) = worker() {
    this.title = title
    this.description = """
        Этот обработчик устанавливает стартовый статус обработки. Запускается только в случае не заданного статуса.
    """.trimIndent()
    on { state == TodoState.NONE }
    handle { state = TodoState.RUNNING }
}