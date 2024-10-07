package ru.otus.todo.repo.postgresql

import com.benasher44.uuid.uuid4
import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.repo.DbTodoIdRequest
import ru.otus.todo.common.repo.DbTodoListRequest
import ru.otus.todo.common.repo.DbTodoRequest
import ru.otus.todo.common.repo.IDbTodoResponse
import ru.otus.todo.common.repo.IDbTodosResponse
import ru.otus.todo.common.repo.IRepoTodo
import ru.otus.todo.repo.common.IRepoTodoInitializable


@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class RepoTodoSql(
    properties: SqlProperties,
    randomUuid: () -> String = { uuid4().toString() },
) : IRepoTodo, IRepoTodoInitializable {
    override fun save(todos: Collection<Todo>): Collection<Todo>
    override suspend fun createTodo(rq: DbTodoRequest): IDbTodoResponse
    override suspend fun readTodo(rq: DbTodoIdRequest): IDbTodoResponse
    override suspend fun updateTodo(rq: DbTodoRequest): IDbTodoResponse
    override suspend fun deleteTodo(rq: DbTodoIdRequest): IDbTodoResponse
    override suspend fun listTodo(rq: DbTodoListRequest): IDbTodosResponse
    fun clear()
}
