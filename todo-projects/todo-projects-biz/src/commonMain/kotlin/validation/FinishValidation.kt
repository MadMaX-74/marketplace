package ru.otus.todo.biz.validation


import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker

fun ICorChainDsl<TodoContext>.finishTodoValidation(title: String) = worker {
    this.title = title
    on { state == TodoState.RUNNING }
    handle {
        todoValidated = todoValidating
    }
}
