package ru.otus.todo.repo.tests

import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.repo.DbTodoListRequest
import ru.otus.todo.common.repo.DbTodosResponseOk
import ru.otus.todo.common.repo.IRepoTodo
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


abstract class RepoTodoListTest {
    abstract val repo: IRepoTodo

    protected open val initializedObjects: List<Todo> = initObjects

    @Test
    fun getTodoList() = runRepoTest {
        val result = repo.listTodo(DbTodoListRequest())
        assertIs<DbTodosResponseOk>(result)
        val expected = listOf(initializedObjects[1], initializedObjects[3]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data.sortedBy { it.id.asString() })
    }

    companion object: BaseInitTodos("search") {
        override val initObjects: List<Todo> = listOf(
            createInitTestModel("todo1"),
            createInitTestModel("todo2"),
            createInitTestModel("todo3"),
            createInitTestModel("todo4"),
            createInitTestModel("todo5"),
        )
    }
}
