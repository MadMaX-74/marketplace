package ru.otus.todo.biz.repo

import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.helpers.fail
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.common.repo.DbTodoListRequest
import ru.otus.todo.common.repo.DbTodosResponseErr
import ru.otus.todo.common.repo.DbTodosResponseOk
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker


fun ICorChainDsl<TodoContext>.repoList(title: String) = worker {
    this.title = title
    description = "Список заданий в БД"
    on { state == TodoState.RUNNING }
    handle {
        val list = DbTodoListRequest()

        when (val dbResponse = todoRepo.listTodo(list)) {
            is DbTodosResponseOk -> todosRepoDone = dbResponse.data.toMutableList()
            is DbTodosResponseErr -> fail(dbResponse.errors)
        }
    }
}
