package ru.otus.todo.biz.stub

import TodoContext
import kotlinx.coroutines.test.runTest
import models.*
import ru.otus.todo.biz.TodoProcessor
import ru.otus.todo.stubs.TodoStub
import stubs.TodoStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class TodoDeleteStubTest {
    private val processor = TodoProcessor()
    val id = TodoId("1")

    @Test
    fun delete() = runTest {

        val ctx = TodoContext(
            command = TodoCommand.DELETE,
            state = TodoState.NONE,
            workMode = TodoWorkMode.STUB,
            stubCase = TodoStubs.SUCCESS,
            todoRequest = Todo(
                id = id.toString(),
            ),
        )
        processor.exec(ctx)

        val stub = TodoStub.get()
        assertEquals(stub.id, ctx.todoResponse.id)
        assertEquals(stub.title, ctx.todoResponse.title)
        assertEquals(stub.description, ctx.todoResponse.description)
        assertEquals(stub.status, ctx.todoResponse.status)
        assertEquals(stub.createdDate, ctx.todoResponse.createdDate)
        assertEquals(stub.completedDate, ctx.todoResponse.completedDate)
    }
}