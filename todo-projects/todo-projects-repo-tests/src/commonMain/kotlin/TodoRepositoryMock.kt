package ru.otus.todo.repo.tests

import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.repo.DbTodoIdRequest
import ru.otus.todo.common.repo.DbTodoListRequest
import ru.otus.todo.common.repo.DbTodoRequest
import ru.otus.todo.common.repo.DbTodoResponseOk
import ru.otus.todo.common.repo.DbTodosResponseOk
import ru.otus.todo.common.repo.IDbTodoResponse
import ru.otus.todo.common.repo.IDbTodosResponse
import ru.otus.todo.common.repo.IRepoTodo


class TodoRepositoryMock(
    private val invokeCreateTodo: (DbTodoRequest) -> IDbTodoResponse = { DEFAULT_Todo_SUCCESS_EMPTY_MOCK },
    private val invokeReadTodo: (DbTodoIdRequest) -> IDbTodoResponse = { DEFAULT_Todo_SUCCESS_EMPTY_MOCK },
    private val invokeUpdateTodo: (DbTodoRequest) -> IDbTodoResponse = { DEFAULT_Todo_SUCCESS_EMPTY_MOCK },
    private val invokeDeleteTodo: (DbTodoIdRequest) -> IDbTodoResponse = { DEFAULT_Todo_SUCCESS_EMPTY_MOCK },
    private val invokeListTodo: (DbTodoListRequest) -> IDbTodosResponse = { DEFAULT_TodoS_SUCCESS_EMPTY_MOCK },
): IRepoTodo {
    override suspend fun createTodo(rq: DbTodoRequest): IDbTodoResponse {
        return invokeCreateTodo(rq)
    }

    override suspend fun readTodo(rq: DbTodoIdRequest): IDbTodoResponse {
        return invokeReadTodo(rq)
    }

    override suspend fun updateTodo(rq: DbTodoRequest): IDbTodoResponse {
        return invokeUpdateTodo(rq)
    }

    override suspend fun deleteTodo(rq: DbTodoIdRequest): IDbTodoResponse {
        return invokeDeleteTodo(rq)
    }

    override suspend fun listTodo(rq: DbTodoListRequest): IDbTodosResponse {
        return invokeListTodo(rq)
    }

    companion object {
        val DEFAULT_Todo_SUCCESS_EMPTY_MOCK = DbTodoResponseOk(Todo())
        val DEFAULT_TodoS_SUCCESS_EMPTY_MOCK = DbTodosResponseOk(emptyList())
    }
}
