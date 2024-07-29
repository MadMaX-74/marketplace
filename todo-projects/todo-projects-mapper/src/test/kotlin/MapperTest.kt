package ru.otus.otuskotlin.marketplace.mappers.v1


import TodoContext
import models.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.otus.todo.api.v1.models.*
import stubs.TodoStubs

class MapperTest {

    @Test
    fun `test fromTransport Create Request`() {
        val request = TaskCreateRequest(
            requestType = "create",
            debug = TaskDebug(
                mode = TaskDebugMode.PROD,
                stub = TaskRequestDebugStubs.SUCCESS
            ),
            task = TaskCreateObject(
                title = "Test Title",
                description = "Test Description",
                status = TaskStatus.IN_PROGRESS
            )
        )

        val expected = TodoContext(
            command = TodoCommand.CREATE,
            todoRequest = Todo(
                title = "Test Title",
                description = "Test Description",
                status = TodoStatus.IN_PROGRESS
            ),
            workMode = TodoWorkMode.PROD,
            stubCase = TodoStubs.SUCCESS
        )

        val actual = TodoContext().apply { fromTransport(request) }
        assertEquals(expected, actual)
    }

    @Test
    fun `test fromTransport Read Request`() {
        val request = TaskReadRequest(
            requestType = "read",
            debug = TaskDebug(
                mode = TaskDebugMode.TEST,
                stub = TaskRequestDebugStubs.BAD_ID
            ),
            id = "12345"
        )

        val expected = TodoContext(
            command = TodoCommand.READ,
            todoRequest = Todo( id = "12345"),
            workMode = TodoWorkMode.TEST,
            stubCase = TodoStubs.BAD_ID
        )

        val actual = TodoContext().apply { fromTransport(request) }
        assertEquals(expected, actual)
    }




    @Test
    fun `test toTransport Create Response`() {
        val context = TodoContext(
            command = TodoCommand.CREATE,
            state = TodoState.RUNNING,
            errors = listOf(TodoError(code = "404", message = "Not Found")),
            todoResponse = Todo(
                id = "123",
                title = "Test Title",
                description = "Test Description",
                status = TodoStatus.COMPLETED
            )
        )

        val expected = TaskCreateResponse(
            result = ResponseResult.SUCCESS,
            errors = listOf(Error(code = "404", message = "Not Found")),
            task = TaskResponseObject(
                id = "123",
                title = "Test Title",
                description = "Test Description",
                status = TaskStatus.COMPLETED
            )
        )

        val actual = context.toTransportCreate()
        assertEquals(expected, actual)
    }

    @Test
    fun `test toTransport Delete Response`() {
        val context = TodoContext(
            command = TodoCommand.DELETE,
            state = TodoState.FAILING,
            errors = listOf(TodoError(code = "500", message = "Internal Server Error"))
        )

        val expected = TaskDeleteResponse(
            result = ResponseResult.ERROR,
            errors = listOf(Error(code = "500", message = "Internal Server Error"))
        )

        val actual = context.toTransportDelete()
        assertEquals(expected, actual)
    }
}

