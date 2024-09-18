package ru.otus.todo.biz.stubs

import TodoContext
import models.TodoState
import models.TodoWorkMode
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.chain

fun ICorChainDsl<TodoContext>.stubs(title: String, block: ICorChainDsl<TodoContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == TodoWorkMode.STUB && state == TodoState.RUNNING }
}