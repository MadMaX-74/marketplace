package ru.otus.todo.repo.tests

import kotlinx.coroutines.test.runTest
import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.repo.DbTodoIdRequest
import ru.otus.todo.common.repo.DbTodoListRequest
import ru.otus.todo.common.repo.DbTodoRequest
import ru.otus.todo.common.repo.DbTodoResponseOk
import ru.otus.todo.common.repo.DbTodosResponseOk
import ru.otus.todo.stubs.TodoStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class TodoRepositoryMockTest {
    private val repo = TodoRepositoryMock(
        invokeCreateTodo = { DbTodoResponseOk(TodoStub.prepareResult { title = "create" }) },
        invokeReadTodo = { DbTodoResponseOk(TodoStub.prepareResult { title = "read" }) },
        invokeUpdateTodo = { DbTodoResponseOk(TodoStub.prepareResult { title = "update" }) },
        invokeDeleteTodo = { DbTodoResponseOk(TodoStub.prepareResult { title = "delete" }) },
        invokeListTodo = { DbTodosResponseOk(listOf(TodoStub.prepareResult { title = "list" })) },
    )

    @Test
    fun mockCreate() = runTest {
        val result = repo.createTodo(DbTodoRequest(Todo()))
        assertIs<DbTodoResponseOk>(result)
        assertEquals("create", result.data.title)
    }

    @Test
    fun mockRead() = runTest {
        val result = repo.readTodo(DbTodoIdRequest(Todo()))
        assertIs<DbTodoResponseOk>(result)
        assertEquals("read", result.data.title)
    }

    @Test
    fun mockUpdate() = runTest {
        val result = repo.updateTodo(DbTodoRequest(Todo()))
        assertIs<DbTodoResponseOk>(result)
        assertEquals("update", result.data.title)
    }

    @Test
    fun mockDelete() = runTest {
        val result = repo.deleteTodo(DbTodoIdRequest(Todo()))
        assertIs<DbTodoResponseOk>(result)
        assertEquals("delete", result.data.title)
    }

    @Test
    fun mockList() = runTest {
        val result = repo.listTodo(DbTodoListRequest())
        assertIs<DbTodosResponseOk>(result)
        assertEquals("list", result.data.first().title)
    }

}
