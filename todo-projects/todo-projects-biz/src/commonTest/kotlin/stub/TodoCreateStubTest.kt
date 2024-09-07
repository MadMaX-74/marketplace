package ru.otus.todo.biz.stub


import TodoContext
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.toInstant
import models.*
import ru.otus.todo.biz.TodoProcessor
import ru.otus.todo.stubs.TodoStub
import stubs.TodoStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class TodoCreateStubTest {
    private val processor = TodoProcessor()
    val id = TodoId("1")
    val title = "title 1"
    val description = "desc 1"
    val status = TodoStatus.IN_PROGRESS
    val createdDate = "2020-01-01T00:00:00Z".toInstant()
    val completedDate = null

    @Test
    fun create() = runTest {

        val ctx = TodoContext(
            command = TodoCommand.CREATE,
            state = TodoState.NONE,
            workMode = TodoWorkMode.STUB,
            stubCase = TodoStubs.SUCCESS,
            todoRequest = Todo(
                id = id.toString(),
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
                id = id.toString(),
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
                id = id.toString(),
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
                id = id.toString(),
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