package ru.otus.todo.repo.tests

import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.models.TodoId
import ru.otus.todo.common.repo.DbTodoIdRequest
import ru.otus.todo.common.repo.DbTodoResponseErr
import ru.otus.todo.common.repo.DbTodoResponseErrWithData
import ru.otus.todo.common.repo.DbTodoResponseOk
import ru.otus.todo.common.repo.IRepoTodo
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull

abstract class RepoTodoDeleteTest {
    abstract val repo: IRepoTodo
    protected open val deleteSucc = initObjects[0]
    protected open val deleteConc = initObjects[1]
    protected open val notFoundId = TodoId("todo-repo-delete-notFound")

    @Test
    fun deleteSuccess() = runRepoTest {
        val result = repo.deleteTodo(DbTodoIdRequest(deleteSucc.id))
        assertIs<DbTodoResponseOk>(result)
        assertEquals(deleteSucc.title, result.data.title)
        assertEquals(deleteSucc.description, result.data.description)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readTodo(DbTodoIdRequest(notFoundId))

        assertIs<DbTodoResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertNotNull(error)
    }

    @Test
    fun deleteConcurrency() = runRepoTest {
        val result = repo.deleteTodo(DbTodoIdRequest(deleteConc.id))

        assertIs<DbTodoResponseErrWithData>(result)
        val error = result.errors.find { it.code == "repo-concurrency" }
        assertNotNull(error)
    }

    companion object : BaseInitTodos("delete") {
        override val initObjects: List<Todo> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteLock"),
        )
    }
}
