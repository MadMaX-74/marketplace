package ru.otus.todo.biz.repo

import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.common.models.TodoWorkMode
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker

fun ICorChainDsl<TodoContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != TodoWorkMode.STUB }
    handle {
        todoResponse = todoRepoDone
        todosResponse = todosRepoDone
        state = when (val st = state) {
            TodoState.RUNNING -> TodoState.FINISHING
            else -> st
        }
    }
}
