package ru.otus.todo.biz.validation

import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.chain

fun ICorChainDsl<TodoContext>.validation(block: ICorChainDsl<TodoContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == TodoState.RUNNING }
}