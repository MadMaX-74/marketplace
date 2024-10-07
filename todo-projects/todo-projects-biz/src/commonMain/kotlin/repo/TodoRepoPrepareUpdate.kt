package ru.otus.todo.biz.repo

import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker

fun ICorChainDsl<TodoContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == TodoState.RUNNING }
    handle {
        todoRepoPrepare = todoRepoRead.deepCopy().apply {
            this.title = todoValidated.title
            description = todoValidated.description
            status = todoValidated.status
            createdDate = todoValidated.createdDate
            completedDate = todoValidated.completedDate
        }
    }
}
