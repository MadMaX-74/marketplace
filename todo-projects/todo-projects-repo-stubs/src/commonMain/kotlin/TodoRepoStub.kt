package ru.otus.todo.backend.repository.inmemory

import ru.otus.todo.common.models.TodoStatus
import ru.otus.todo.common.repo.DbTodoIdRequest
import ru.otus.todo.common.repo.DbTodoListRequest
import ru.otus.todo.common.repo.DbTodoRequest
import ru.otus.todo.common.repo.DbTodoResponseOk
import ru.otus.todo.common.repo.DbTodosResponseOk
import ru.otus.todo.common.repo.IDbTodoResponse
import ru.otus.todo.common.repo.IDbTodosResponse
import ru.otus.todo.common.repo.IRepoTodo
import ru.otus.todo.stubs.TodoStub


class TodoRepoStub() : IRepoTodo {
    override suspend fun createTodo(rq: DbTodoRequest): IDbTodoResponse {
        return DbTodoResponseOk(
            data = TodoStub.get(),
        )
    }

    override suspend fun readTodo(rq: DbTodoIdRequest): IDbTodoResponse {
        return DbTodoResponseOk(
            data = TodoStub.get(),
        )
    }

    override suspend fun updateTodo(rq: DbTodoRequest): IDbTodoResponse {
        return DbTodoResponseOk(
            data = TodoStub.get(),
        )
    }

    override suspend fun deleteTodo(rq: DbTodoIdRequest): IDbTodoResponse {
        return DbTodoResponseOk(
            data = TodoStub.get(),
        )
    }

    override suspend fun listTodo(rq: DbTodoListRequest): IDbTodosResponse {
        return DbTodosResponseOk(
            data = TodoStub.prepareTaskList(status = TodoStatus.IN_PROGRESS),
        )
    }
}
