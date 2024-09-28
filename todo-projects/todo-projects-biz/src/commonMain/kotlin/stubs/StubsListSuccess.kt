package ru.otus.todo.biz.stubs


import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.TodoCorSettings
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.common.models.TodoStatus
import ru.otus.todo.common.stubs.TodoStubs
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker
import ru.otus.todo.stubs.TodoStub

fun ICorChainDsl<TodoContext>.stubListSuccess(title: String, corSettings: TodoCorSettings) = worker {
    this.title = title
    this.description = """
        Кейс успеха для получения списка задач
    """.trimIndent()
    on { stubCase == TodoStubs.SUCCESS && state == TodoState.RUNNING }
    handle {
        state = TodoState.FINISHING
        todosResponse.addAll(TodoStub.prepareTaskList(TodoStatus.IN_PROGRESS))
    }
}