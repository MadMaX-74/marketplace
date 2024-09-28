package ru.otus.todo.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.models.TodoId
import ru.otus.todo.common.repo.DbTodoIdRequest
import ru.otus.todo.common.repo.DbTodoListRequest
import ru.otus.todo.common.repo.DbTodoRequest
import ru.otus.todo.common.repo.DbTodoResponseOk
import ru.otus.todo.common.repo.DbTodosResponseOk
import ru.otus.todo.common.repo.IDbTodoResponse
import ru.otus.todo.common.repo.IDbTodosResponse
import ru.otus.todo.common.repo.IRepoTodo
import ru.otus.todo.common.repo.TodoRepoBase
import ru.otus.todo.common.repo.errorDb
import ru.otus.todo.common.repo.errorEmptyId
import ru.otus.todo.common.repo.errorNotFound
import ru.otus.todo.repo.common.IRepoTodoInitializable
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class TodoRepoInMemory(
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : TodoRepoBase(), IRepoTodo, IRepoTodoInitializable {

    private val mutex: Mutex = Mutex()
    private val cache = Cache.Builder<String, TodoEntity>()
        .expireAfterWrite(ttl)
        .build()

    override fun save(todos: Collection<Todo>) = todos.map { todo ->
        val entity = TodoEntity(todo)
        require(entity.id != null)
        cache.put(entity.id, entity)
        todo
    }

    override suspend fun createTodo(rq: DbTodoRequest): IDbTodoResponse = tryAdMethod {
        val key = randomUuid()
        val ad = rq.todo.copy(id = TodoId(key))
        val entity = TodoEntity(ad)
        mutex.withLock {
            cache.put(key, entity)
        }
        DbTodoResponseOk(ad)
    }

    override suspend fun readTodo(rq: DbTodoIdRequest): IDbTodoResponse = tryAdMethod {
        val key = rq.id.takeIf { it != TodoId.NONE }?.asString() ?: return@tryAdMethod errorEmptyId
        mutex.withLock {
            cache.get(key)
                ?.let {
                    DbTodoResponseOk(it.toInternal())
                } ?: errorNotFound(rq.id)
        }
    }

    override suspend fun updateTodo(rq: DbTodoRequest): IDbTodoResponse = tryAdMethod {
        val rqTodo = rq.todo
        val id = rqTodo.id.takeIf { it != TodoId.NONE } ?: return@tryAdMethod errorEmptyId
        val key = id.asString()

        mutex.withLock {
            val oldTodo = cache.get(key)?.toInternal()
            when {
                else -> {
                    val newTodo = rqTodo.copy()
                    val entity = TodoEntity(newTodo)
                    cache.put(key, entity)
                    DbTodoResponseOk(newTodo)
                }
            }
        }
    }


    override suspend fun deleteTodo(rq: DbTodoIdRequest): IDbTodoResponse = tryAdMethod {
        val id = rq.id.takeIf { it != TodoId.NONE } ?: return@tryAdMethod errorEmptyId
        val key = id.asString()

        mutex.withLock {
            val oldTodo = cache.get(key)?.toInternal()
            when {
                oldTodo == null -> errorNotFound(id)
                else -> {
                    cache.invalidate(key)
                    DbTodoResponseOk(oldTodo)
                }
            }
        }
    }

    /**
     * Поиск объявлений по фильтру
     * Если в фильтре не установлен какой-либо из параметров - по нему фильтрация не идет
     */
    override suspend fun listTodo(rq: DbTodoListRequest): IDbTodosResponse = tryAdsMethod {
        val result: List<Todo> = cache.asMap().values.map { it.toInternal() }
        DbTodosResponseOk(result)
    }
}
