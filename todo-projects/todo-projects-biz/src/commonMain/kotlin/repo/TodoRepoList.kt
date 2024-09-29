package ru.otus.todo.biz.repo

import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.helpers.fail
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.common.repo.DbTodoListRequest
import ru.otus.todo.common.repo.DbTodosResponseErr
import ru.otus.todo.common.repo.DbTodosResponseOk
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker


fun ICorChainDsl<TodoContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск объявлений в БД по фильтру"
    on { state == TodoState.RUNNING }
    handle {
        val request = DbTodoListRequest()
        when(val result = todoRepo.listTodo(request)) {
            is DbTodosResponseOk -> todosRepoDone = result.data.toMutableList()
            is DbTodosResponseErr -> fail(result.errors)
        }
    }
}
