package ru.otus.todo.biz.stubs

import TodoContext
import TodoCorSettings
import models.TodoState
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker
import ru.otus.todo.stubs.TodoStub
import stubs.TodoStubs

fun ICorChainDsl<TodoContext>.stubDeleteSuccess(title: String, corSettings: TodoCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для удаления задачи
    """.trimIndent()
    on { stubCase == TodoStubs.SUCCESS && state == TodoState.RUNNING }
    handle {
        state = TodoState.FINISHING
        val stub = TodoStub.prepareResult {
            todoRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
        }
        todoResponse = stub
    }
}