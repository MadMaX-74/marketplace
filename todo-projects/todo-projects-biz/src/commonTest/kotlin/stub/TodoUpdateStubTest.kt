package ru.otus.todo.biz.stub

import TodoContext
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.toInstant
import models.*
import ru.otus.todo.biz.TodoProcessor
import stubs.TodoStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class TodoUpdateStubTest {
    private val processor = TodoProcessor()
    val id = TodoId("2").toString()
    val title = "title 2"
    val description = "desc 2"
    val status = TodoStatus.IN_PROGRESS
    val createdDate = "2020-01-01T00:00:00Z".toInstant()
    val completedDate = "2024-01-01T00:00:00Z".toInstant()

    @Test
    fun update() = runTest {

        val ctx = TodoContext(
            command = TodoCommand.UPDATE,
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
        assertEquals(id, ctx.todoResponse.id)
        assertEquals(title, ctx.todoResponse.title)
        assertEquals(description, ctx.todoResponse.description)
        assertEquals(status, ctx.todoResponse.status)
        assertEquals(createdDate, ctx.todoResponse.createdDate)
        assertEquals(completedDate, ctx.todoResponse.completedDate)
    }
}