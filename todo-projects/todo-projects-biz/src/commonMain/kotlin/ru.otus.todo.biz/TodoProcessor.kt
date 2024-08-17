package ru.otus.todo.biz

import TodoContext
import TodoCorSettings
import models.TodoState
import models.TodoStatus
import ru.otus.otuskotlin.marketplace.stubs.TodoStub


@Suppress("unused", "RedundantSuspendModifier")
class TodoProcessor(val corSettings: TodoCorSettings) {
    suspend fun exec(ctx: TodoContext) {
        ctx.todoResponse = TodoStub.get()
        ctx.todosResponse = TodoStub.prepareTaskList(TodoStatus.IN_PROGRESS).toMutableList()
        ctx.state = TodoState.RUNNING
    }
}
