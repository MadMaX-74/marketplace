package ru.otus.todo.biz.stubs

import TodoContext
import TodoCorSettings
import models.TodoState
import models.TodoStatus
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker
import ru.otus.todo.stubs.TodoStub
import stubs.TodoStubs

fun ICorChainDsl<TodoContext>.stubCreateSuccess(title: String, corSettings: TodoCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для создания задачи
    """.trimIndent()
    on { stubCase == TodoStubs.SUCCESS && state == TodoState.RUNNING }
    handle {
        state = TodoState.FINISHING
        val stub = TodoStub.prepareResult {
            todoRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
            todoRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
            todoRequest.status.takeIf {it != TodoStatus.NONE }?.also { this.status = it }
        }
        todoResponse = stub
    }
}