package ru.otus.todo.biz.repo

import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.helpers.fail
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.common.repo.DbTodoRequest
import ru.otus.todo.common.repo.DbTodoResponseErr
import ru.otus.todo.common.repo.DbTodoResponseErrWithData
import ru.otus.todo.common.repo.DbTodoResponseOk
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker



fun ICorChainDsl<TodoContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == TodoState.RUNNING }
    handle {
        val request = DbTodoRequest(todoRepoPrepare)
        when(val result = todoRepo.updateTodo(request)) {
            is DbTodoResponseOk -> todoRepoDone = result.data
            is DbTodoResponseErr -> fail(result.errors)
            is DbTodoResponseErrWithData -> {
                fail(result.errors)
                todoRepoDone = result.data
            }
        }
    }
}
