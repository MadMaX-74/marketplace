package ru.otus.todo.mapper


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
    fun `test toTransport Create Response`() {
        val context = TodoContext(
            command = TodoCommand.CREATE,
            state = TodoState.RUNNING,
            errors = mutableListOf(TodoError(code = "404", message = "Not Found")),
            todoResponse = Todo(
                id = "123",
                title = "Test Title",
                description = "Test Description",
                status = TodoStatus.COMPLETED
            )
        )

        val expected = TaskCreateResponse(
            result = ResponseResult.SUCCESS,
            errors = mutableListOf(Error(code = "404", message = "Not Found")),
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
    fun `test fromTransport Read Request`() {
        val request = TaskReadRequest(
            requestType = "read",
            debug = TaskDebug(
                mode = TaskDebugMode.TEST,
                stub = TaskRequestDebugStubs.BAD_ID
            ),
            task = TaskReadObject(
                id = "12345"
            )
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
    fun `test toTransport Read Response`() {
        val context = TodoContext(
            command = TodoCommand.READ,
            state = TodoState.RUNNING,
            errors = mutableListOf(TodoError(code = "404", message = "Not Found")),
            todoResponse = Todo(
                id = "123",
                title = "Test Title",
                description = "Test Description",
                status = TodoStatus.COMPLETED
            )
        )

        val expected = TaskReadResponse(
            result = ResponseResult.SUCCESS,
            errors = listOf(Error(code = "404", message = "Not Found")),
            task = TaskResponseObject(
                id = "123",
                title = "Test Title",
                description = "Test Description",
                status = TaskStatus.COMPLETED
            )
        )

        val actual = context.toTransportRead()
        assertEquals(expected, actual)
    }
    @Test
    fun `test fromTransport Update Request`() {
        val request = TaskUpdateRequest(
            requestType = "update",
            debug = TaskDebug(
                mode = TaskDebugMode.PROD,
                stub = TaskRequestDebugStubs.SUCCESS
            ),
            task = TaskUpdateObject(
                id = "12345",
                title = "Updated Title",
                description = "Updated Description",
                status = TaskStatus.COMPLETED
            )
        )

        val expected = TodoContext(
            command = TodoCommand.UPDATE,
            todoRequest = Todo(
                id = TodoId("12345").toString(),  // Используем TodoId
                title = "Updated Title",
                description = "Updated Description",
                status = TodoStatus.COMPLETED
            ),
            workMode = TodoWorkMode.PROD,
            stubCase = TodoStubs.SUCCESS
        )

        val actual = TodoContext().apply { fromTransport(request) }
        assertEquals(expected, actual)
    }

    @Test
    fun `test toTransport Update Response`() {
        val context = TodoContext(
            command = TodoCommand.UPDATE,
            state = TodoState.RUNNING,
            errors = mutableListOf(TodoError(code = "404", message = "Not Found")),
            todoResponse = Todo(
                id = "123",
                title = "Updated Title",
                description = "Updated Description",
                status = TodoStatus.COMPLETED
            )
        )

        val expected = TaskUpdateResponse(
            result = ResponseResult.SUCCESS,
            errors = mutableListOf(Error(code = "404", message = "Not Found")),
            task = TaskResponseObject(
                id = "123",
                title = "Updated Title",
                description = "Updated Description",
                status = TaskStatus.COMPLETED
            )
        )

        val actual = context.toTransportUpdate()
        assertEquals(expected, actual)
    }


    @Test
    fun `test fromTransport Delete Request`() {
        val request = TaskDeleteRequest(
            requestType = "delete",
            debug = TaskDebug(
                mode = TaskDebugMode.TEST,
                stub = TaskRequestDebugStubs.NOT_FOUND
            ),
            task = TaskDeleteObject(
                id = "12345"
            )
        )

        val expected = TodoContext(
            command = TodoCommand.DELETE,
            todoRequest = Todo(id = "12345"),
            workMode = TodoWorkMode.TEST,
            stubCase = TodoStubs.NOT_FOUND
        )

        val actual = TodoContext().apply { fromTransport(request) }
        assertEquals(expected, actual)
    }
    @Test
    fun `test toTransport Delete Response`() {
        val context = TodoContext(
            command = TodoCommand.DELETE,
            state = TodoState.FAILING,
            errors = mutableListOf(TodoError(code = "500", message = "Internal Server Error"))
        )

        val expected = TaskDeleteResponse(
            result = ResponseResult.ERROR,
            errors = mutableListOf(Error(code = "500", message = "Internal Server Error"))
        )

        val actual = context.toTransportDelete()
        assertEquals(expected, actual)
    }
    @Test
    fun `test toTransport List Response`() {
        val context = TodoContext(
            command = TodoCommand.LIST,
            state = TodoState.RUNNING,
            errors = mutableListOf(TodoError(code = "404", message = "Not Found")),
            todosResponse = mutableListOf(
                Todo(
                    id = "123",
                    title = "Test Title",
                    description = "Test Description",
                    status = TodoStatus.COMPLETED
                ),
                Todo(
                    id = "124",
                    title = "Another Title",
                    description = "Another Description",
                    status = TodoStatus.IN_PROGRESS
                )
            )
        )

        val expected = TaskListResponse(
            result = ResponseResult.SUCCESS,
            errors = mutableListOf(Error(code = "404", message = "Not Found")),
            tasks = mutableListOf(
                TaskResponseObject(
                    id = "123",
                    title = "Test Title",
                    description = "Test Description",
                    status = TaskStatus.COMPLETED
                ),
                TaskResponseObject(
                    id = "124",
                    title = "Another Title",
                    description = "Another Description",
                    status = TaskStatus.IN_PROGRESS
                )
            )
        )

        val actual = context.toTransportList()
        assertEquals(expected, actual)
    }


}

