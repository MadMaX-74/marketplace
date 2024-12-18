package ru.otus.todo.biz.repo

import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker

fun ICorChainDsl<TodoContext>.repoPrepareList(title: String) = worker {
    this.title = title
    description = "Готовим данные к поиску задач в БД"
    on { state == TodoState.RUNNING }
    handle {
        todoRepoPrepare = todoRepoRead.deepCopy()
        todoRepoDone = todoRepoRead.deepCopy()
    }
}
