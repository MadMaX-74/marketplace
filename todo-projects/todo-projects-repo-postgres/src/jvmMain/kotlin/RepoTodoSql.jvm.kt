package ru.otus.todo.repo.postgresql

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import ru.otus.todo.common.helpers.asTodoError
import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.models.TodoId
import ru.otus.todo.common.repo.DbTodoIdRequest
import ru.otus.todo.common.repo.DbTodoListRequest
import ru.otus.todo.common.repo.DbTodoRequest
import ru.otus.todo.common.repo.DbTodoResponseErr
import ru.otus.todo.common.repo.DbTodoResponseOk
import ru.otus.todo.common.repo.DbTodosResponseErr
import ru.otus.todo.common.repo.DbTodosResponseOk
import ru.otus.todo.common.repo.IDbTodoResponse
import ru.otus.todo.common.repo.IDbTodosResponse
import ru.otus.todo.common.repo.IRepoTodo
import ru.otus.todo.common.repo.errorEmptyId
import ru.otus.todo.common.repo.errorNotFound
import ru.otus.todo.repo.common.IRepoTodoInitializable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class RepoTodoSql actual constructor(
    properties: SqlProperties,
    private val randomUuid: () -> String
) : IRepoTodo, IRepoTodoInitializable {
    private val todoTable = TodoTable("${properties.schema}.${properties.table}")

    private val driver = when {
        properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
        else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
    }

    private val conn = Database.connect(
        properties.url, driver, properties.user, properties.password
    )

    actual fun clear(): Unit = transaction(conn) {
        todoTable.deleteAll()
    }

    private fun saveObj(todo: Todo): Todo = transaction(conn) {
        val res = todoTable
            .insert {
                to(it, todo, randomUuid)
            }
            .resultedValues
            ?.map { todoTable.from(it) }
        res?.first() ?: throw RuntimeException("BD error: insert statement returned empty result")
    }

    private suspend inline fun <T> transactionWrapper(crossinline block: () -> T, crossinline handle: (Exception) -> T): T =
        withContext(Dispatchers.IO) {
            try {
                transaction(conn) {
                    block()
                }
            } catch (e: Exception) {
                handle(e)
            }
        }

    private suspend inline fun transactionWrapper(crossinline block: () -> IDbTodoResponse): IDbTodoResponse =
        transactionWrapper(block) { DbTodoResponseErr(it.asTodoError()) }

    actual override fun save(todos: Collection<Todo>): Collection<Todo> = todos.map { saveObj(it) }
    actual override suspend fun createTodo(rq: DbTodoRequest): IDbTodoResponse = transactionWrapper {
        DbTodoResponseOk(saveObj(rq.todo))
    }

    private fun read(id: TodoId): IDbTodoResponse {
        val res = todoTable.selectAll().where {
            todoTable.id eq id.asString()
        }.singleOrNull() ?: return errorNotFound(id)
        return DbTodoResponseOk(todoTable.from(res))
    }

    actual override suspend fun readTodo(rq: DbTodoIdRequest): IDbTodoResponse = transactionWrapper { read(rq.id) }

    private suspend fun update(
        id: TodoId,
        block: (Todo) -> IDbTodoResponse
    ): IDbTodoResponse =
        transactionWrapper {
            if (id == TodoId.NONE) return@transactionWrapper errorEmptyId

            val current = todoTable.selectAll().where { todoTable.id eq id.asString() }
                .singleOrNull()
                ?.let { todoTable.from(it) }

            when {
                current == null -> errorNotFound(id)
                else -> block(current)
            }
        }


    actual override suspend fun updateTodo(rq: DbTodoRequest): IDbTodoResponse = update(rq.todo.id) {
        todoTable.update({ todoTable.id eq rq.todo.id.asString() }) {
            to(it, rq.todo.copy(), randomUuid)
        }
        read(rq.todo.id)
    }

    actual override suspend fun deleteTodo(rq: DbTodoIdRequest): IDbTodoResponse = update(rq.id) {
        todoTable.deleteWhere { id eq rq.id.asString() }
        DbTodoResponseOk(it)
    }

    actual override suspend fun listTodo(rq: DbTodoListRequest): IDbTodosResponse =
        transactionWrapper({
            val res = todoTable.selectAll().where {
                buildList {
                    add(Op.TRUE)
                }.reduce { a, b -> (a and b) as Op.TRUE }
            }
            DbTodosResponseOk(data = res.map { todoTable.from(it) })
        }, {
            DbTodosResponseErr(it.asTodoError())
        })
}
