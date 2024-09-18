package ru.otus.todo.biz.stub

import TodoContext
import kotlinx.coroutines.test.runTest
import models.*
import ru.otus.todo.biz.TodoProcessor
import ru.otus.todo.stubs.TodoStub
import stubs.TodoStubs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TodoListStubTest {
    private val processor = TodoProcessor()
    @Test
    fun list() = runTest {

        val ctx = TodoContext(
            command = TodoCommand.LIST,
            state = TodoState.NONE,
            workMode = TodoWorkMode.STUB,
            stubCase = TodoStubs.SUCCESS
        )

        processor.exec(ctx)

        assertTrue(ctx.todosResponse.isNotEmpty(), "Expected non-empty list of todos")

        val stubList = TodoStub.prepareTaskList(TodoStatus.IN_PROGRESS)

        with(stubList.first()) {
            assertEquals(this.id, ctx.todosResponse.first().id)
            assertEquals(this.title, ctx.todosResponse.first().title)
            assertEquals(this.description, ctx.todosResponse.first().description)
            assertEquals(this.status, ctx.todosResponse.first().status)
        }
    }

}