package ru.otus.todo.biz.validation

import TodoContext
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import models.*
import ru.otus.todo.biz.TodoProcessor
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationIdCorrect(command: TodoCommand, processor: TodoProcessor) = runTest {
    val ctx = TodoContext(
        command = command,
        state = TodoState.NONE,
        workMode = TodoWorkMode.TEST,
        todoRequest = Todo(
            id = TodoId("123456").toString(),
            title = "abc",
            description = "abc",
            status = TodoStatus.IN_PROGRESS,
            createdDate = Instant.parse("2020-01-01T00:00:00Z"),
            completedDate = null,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(TodoState.FAILING, ctx.state)
}

fun validationIdEmpty(command: TodoCommand, processor: TodoProcessor) = runTest {
    val ctx = TodoContext(
        command = command,
        state = TodoState.NONE,
        workMode = TodoWorkMode.TEST,
        todoRequest = Todo(
            id = "".toString(),
            title = "abc",
            description = "abc",
            status = TodoStatus.IN_PROGRESS,
            createdDate = Instant.parse("2020-01-01T00:00:00Z"),
            completedDate = null
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertEquals(TodoState.RUNNING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}
