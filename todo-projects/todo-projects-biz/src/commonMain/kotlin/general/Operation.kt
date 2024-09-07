package ru.otus.todo.biz.general

import TodoContext
import models.TodoCommand
import models.TodoState
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.chain

fun ICorChainDsl<TodoContext>.operation(
    title: String,
    command: TodoCommand,
    block: ICorChainDsl<TodoContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == TodoState.RUNNING }
}