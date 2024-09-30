package ru.otus.todo.repo.tests

import kotlinx.datetime.Instant
import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.models.TodoId
import ru.otus.todo.common.models.TodoStatus
import ru.otus.todo.common.repo.DbTodoRequest
import ru.otus.todo.common.repo.DbTodoResponseOk
import ru.otus.todo.repo.common.IRepoTodoInitializable
import kotlin.test.*


abstract class RepoTodoCreateTest {
    abstract val repo: IRepoTodoInitializable
    protected open val uuidNew = TodoId("10000000-0000-0000-0000-000000000001")

    private val createObj = Todo(
        title = "create object",
        description = "create object description",
        status = TodoStatus.COMPLETED,
        createdDate = Instant.DISTANT_PAST.toString(),
        completedDate = Instant.DISTANT_FUTURE.toString(),
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createTodo(DbTodoRequest(createObj))
        val expected = createObj
        assertIs<DbTodoResponseOk>(result)
        assertEquals(uuidNew, result.data.id)
        assertEquals(expected.title, result.data.title)
        assertEquals(expected.description, result.data.description)
        assertNotEquals(TodoId.NONE, result.data.id)
    }

    companion object : BaseInitTodos("create") {
        override val initObjects: List<Todo> = emptyList()
    }
}
