package ru.otus.todo.biz.stub

import kotlinx.coroutines.test.runTest
import ru.otus.todo.biz.TodoProcessor
import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.models.TodoCommand
import ru.otus.todo.common.models.TodoId
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.common.models.TodoWorkMode
import ru.otus.todo.common.stubs.TodoStubs
import ru.otus.todo.stubs.TodoStub
import kotlin.test.Test
import kotlin.test.assertEquals

class TodoReadStubTest {
    private val processor = TodoProcessor()
    val id = TodoId("1")

    @Test
    fun read() = runTest {

        val ctx = TodoContext(
            command = TodoCommand.READ,
            state = TodoState.NONE,
            workMode = TodoWorkMode.STUB,
            stubCase = TodoStubs.SUCCESS,
            todoRequest = Todo(
                id = id,
            ),
        )
        processor.exec(ctx)
        with (TodoStub.get()) {
            assertEquals(id, ctx.todoResponse.id)
            assertEquals(title, ctx.todoResponse.title)
            assertEquals(description, ctx.todoResponse.description)
            assertEquals(status, ctx.todoResponse.status)
            assertEquals(createdDate, ctx.todoResponse.createdDate)
            assertEquals(completedDate, ctx.todoResponse.completedDate)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = TodoContext(
            command = TodoCommand.READ,
            state = TodoState.NONE,
            workMode = TodoWorkMode.STUB,
            stubCase = TodoStubs.BAD_ID,
            todoRequest = Todo(),
        )
        processor.exec(ctx)
        assertEquals(Todo(), ctx.todoResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

}