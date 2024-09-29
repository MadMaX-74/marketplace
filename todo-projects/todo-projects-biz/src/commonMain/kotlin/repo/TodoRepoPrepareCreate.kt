package ru.otus.todo.biz.repo

import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker


fun ICorChainDsl<TodoContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == TodoState.RUNNING }
    handle {
        todoRepoPrepare = todoValidated.deepCopy()
        // TODO будет реализовано в занятии по управлению пользвателями
    }
}
