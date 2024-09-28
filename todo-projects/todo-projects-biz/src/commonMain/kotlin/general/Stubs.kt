package ru.otus.todo.biz.general

import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.common.models.TodoWorkMode
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.chain

fun ICorChainDsl<TodoContext>.stubs(title: String, block: ICorChainDsl<TodoContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == TodoWorkMode.STUB && state == TodoState.RUNNING }
}