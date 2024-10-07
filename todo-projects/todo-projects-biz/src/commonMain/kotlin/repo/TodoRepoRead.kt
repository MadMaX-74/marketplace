package ru.otus.todo.biz.repo

import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.helpers.fail
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.common.repo.DbTodoIdRequest
import ru.otus.todo.common.repo.DbTodoResponseErr
import ru.otus.todo.common.repo.DbTodoResponseErrWithData
import ru.otus.todo.common.repo.DbTodoResponseOk
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker


fun ICorChainDsl<TodoContext>.repoRetodo(title: String) = worker {
    this.title = title
    description = "Чтение объявления из БД"
    on { state == TodoState.RUNNING }
    handle {
        val request = DbTodoIdRequest(todoValidated)
        when(val result = todoRepo.readTodo(request)) {
            is DbTodoResponseOk -> todoRepoRead = result.data
            is DbTodoResponseErr -> fail(result.errors)
            is DbTodoResponseErrWithData -> {
                fail(result.errors)
                todoRepoRead = result.data
            }
        }
    }
}
