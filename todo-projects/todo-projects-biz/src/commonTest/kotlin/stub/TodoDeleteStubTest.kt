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
                id = id,
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