package ru.otus.todo.biz.validation

import TodoContext
import models.TodoState
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.chain

fun ICorChainDsl<TodoContext>.validation(block: ICorChainDsl<TodoContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == TodoState.RUNNING }
}