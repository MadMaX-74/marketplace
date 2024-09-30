package ru.otus.todo.biz.validation

import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import ru.otus.todo.biz.TodoProcessor
import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.models.TodoCommand
import ru.otus.todo.common.models.TodoId
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.common.models.TodoStatus
import ru.otus.todo.common.models.TodoWorkMode
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationIdCorrect(command: TodoCommand, processor: TodoProcessor) = runTest {
    val ctx = TodoContext(
        command = command,
        state = TodoState.NONE,
        workMode = TodoWorkMode.TEST,
        todoRequest = Todo(
            id = TodoId("123456"),
            title = "abc",
            description = "abc",
            status = TodoStatus.IN_PROGRESS,
            createdDate = "2020-01-01T00:00:00Z",
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
            id = TodoId.NONE,
            title = "abc",
            description = "abc",
            status = TodoStatus.IN_PROGRESS,
            createdDate = "2020-01-01T00:00:00Z",
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
