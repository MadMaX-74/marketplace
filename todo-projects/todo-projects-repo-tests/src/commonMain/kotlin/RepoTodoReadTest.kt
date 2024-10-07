package ru.otus.todo.repo.tests


import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.models.TodoId
import ru.otus.todo.common.repo.DbTodoIdRequest
import ru.otus.todo.common.repo.DbTodoResponseErr
import ru.otus.todo.common.repo.DbTodoResponseOk
import ru.otus.todo.common.repo.IRepoTodo
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


abstract class RepoTodoReadTest {
    abstract val repo: IRepoTodo
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readTodo(DbTodoIdRequest(readSucc.id))

        assertIs<DbTodoResponseOk>(result)
        assertEquals(readSucc, result.data)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readTodo(DbTodoIdRequest(notFoundId))

        assertIs<DbTodoResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitTodos("delete") {
        override val initObjects: List<Todo> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = TodoId("todo-repo-read-notFound")

    }
}
