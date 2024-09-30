package ru.otus.todo.biz.stub


import kotlinx.coroutines.test.runTest
import kotlinx.datetime.toInstant
import ru.otus.todo.biz.TodoProcessor
import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.models.TodoCommand
import ru.otus.todo.common.models.TodoId
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.common.models.TodoStatus
import ru.otus.todo.common.models.TodoWorkMode
import ru.otus.todo.common.stubs.TodoStubs
import ru.otus.todo.stubs.TodoStub
import kotlin.test.Test
import kotlin.test.assertEquals

class TodoCreateStubTest {
    private val processor = TodoProcessor()
    val id = TodoId("1")
    val title = "title 1"
    val description = "desc 1"
    val status = TodoStatus.IN_PROGRESS
    val createdDate = "2020-01-01T00:00:00Z"
    val completedDate = null

    @Test
    fun create() = runTest {

        val ctx = TodoContext(
            command = TodoCommand.CREATE,
            state = TodoState.NONE,
            workMode = TodoWorkMode.STUB,
            stubCase = TodoStubs.SUCCESS,
            todoRequest = Todo(
                id = id,
                title = title,
                description = description,
                status = status,
                createdDate = createdDate,
                completedDate = completedDate,
            ),
        )
        processor.exec(ctx)
        assertEquals(TodoStub.get().id, ctx.todoResponse.id)
        assertEquals(title, ctx.todoResponse.title)
        assertEquals(description, ctx.todoResponse.description)
        assertEquals(status, ctx.todoResponse.status)
        assertEquals(createdDate, ctx.todoResponse.createdDate)
        assertEquals(completedDate, ctx.todoResponse.completedDate)
    }

    @Test
    fun badDescription() = runTest {
        val ctx = TodoContext(
            command = TodoCommand.CREATE,
            state = TodoState.NONE,
            workMode = TodoWorkMode.STUB,
            stubCase = TodoStubs.BAD_DESCRIPTION,
            todoRequest = Todo(
                id = id,
                title = title,
                description = "",
                status = status,
                createdDate = createdDate,
                completedDate = completedDate,
            ),
        )
        processor.exec(ctx)
        assertEquals(Todo(), ctx.todoResponse)
        assertEquals("description", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = TodoContext(
            command = TodoCommand.CREATE,
            state = TodoState.NONE,
            workMode = TodoWorkMode.STUB,
            stubCase = TodoStubs.DB_ERROR,
            todoRequest = Todo(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(Todo(), ctx.todoResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = TodoContext(
            command = TodoCommand.CREATE,
            state = TodoState.NONE,
            workMode = TodoWorkMode.STUB,
            stubCase = TodoStubs.BAD_ID,
            todoRequest = Todo(
                id = id,
                title = title,
                description = description,
                status = status,
                createdDate = createdDate,
                completedDate = completedDate,
            ),
        )
        processor.exec(ctx)
        assertEquals(Todo(), ctx.todoResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}