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


fun ICorChainDsl<TodoContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление объявления из БД по ID"
    on { state == TodoState.RUNNING }
    handle {
        val request = DbTodoIdRequest(todoRepoPrepare)
        when(val result = todoRepo.deleteTodo(request)) {
            is DbTodoResponseOk -> todoRepoDone = result.data
            is DbTodoResponseErr -> {
                fail(result.errors)
                todoRepoDone = todoRepoRead
            }
            is DbTodoResponseErrWithData -> {
                fail(result.errors)
                todoRepoDone = result.data
            }
        }
    }
}
