package ru.otus.todo.biz.stubs

import TodoContext
import TodoCorSettings
import models.TodoId
import models.TodoState
import models.TodoStatus
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker
import ru.otus.todo.stubs.TodoStub
import stubs.TodoStubs

fun ICorChainDsl<TodoContext>.stubUpdateSuccess(title: String, corSettings: TodoCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для изменения задачи
    """.trimIndent()
    on { stubCase == TodoStubs.SUCCESS && state == TodoState.RUNNING }
    handle {
        state = TodoState.FINISHING
        val stub = TodoStub.prepareResult {
            todoRequest.id.takeIf { it != TodoId.NONE.toString() }?.also { this.id = it }
            todoRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
            todoRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
            todoRequest.status.takeIf { it != TodoStatus.COMPLETED }?.also { this.status = it }
            todoRequest.completedDate.takeIf { it != null }?.also { this.completedDate = it }
        }
        todoResponse = stub
    }
}