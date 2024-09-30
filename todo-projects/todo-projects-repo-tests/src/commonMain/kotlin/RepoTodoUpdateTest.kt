package ru.otus.todo.repo.tests

import kotlinx.datetime.Instant
import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.models.TodoId
import ru.otus.todo.common.models.TodoStatus
import ru.otus.todo.common.repo.DbTodoRequest
import ru.otus.todo.common.repo.DbTodoResponseErr
import ru.otus.todo.common.repo.DbTodoResponseErrWithData
import ru.otus.todo.common.repo.DbTodoResponseOk
import ru.otus.todo.common.repo.IRepoTodo
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


abstract class RepoTodoUpdateTest {
    abstract val repo: IRepoTodo
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = TodoId("todo-repo-update-not-found")

    private val reqUpdateSucc by lazy {
        Todo(
            id = updateSucc.id,
            title = "update object",
            description = "update object description",
            status = TodoStatus.COMPLETED,
            createdDate = Instant.DISTANT_PAST.toString(),
            completedDate = Instant.DISTANT_FUTURE.toString(),
        )
    }
    private val reqUpdateNotFound = Todo(
        id = updateIdNotFound,
        title = "update object not found",
        description = "update object not found description",
        status = TodoStatus.COMPLETED,
        createdDate = Instant.DISTANT_PAST.toString(),
        completedDate = Instant.DISTANT_FUTURE.toString(),
    )
    private val reqUpdateConc by lazy {
        Todo(
            id = updateConc.id,
            title = "update object not found",
            description = "update object not found description",
            status = TodoStatus.COMPLETED,
            createdDate = Instant.DISTANT_PAST.toString(),
            completedDate = Instant.DISTANT_FUTURE.toString(),
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateTodo(DbTodoRequest(reqUpdateSucc))
        assertIs<DbTodoResponseOk>(result)
        assertEquals(reqUpdateSucc.id, result.data.id)
        assertEquals(reqUpdateSucc.title, result.data.title)
        assertEquals(reqUpdateSucc.description, result.data.description)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateTodo(DbTodoRequest(reqUpdateNotFound))
        assertIs<DbTodoResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateTodo(DbTodoRequest(reqUpdateConc))
        assertIs<DbTodoResponseErrWithData>(result)
        val error = result.errors.find { it.code == "repo-concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitTodos("update") {
        override val initObjects: List<Todo> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
