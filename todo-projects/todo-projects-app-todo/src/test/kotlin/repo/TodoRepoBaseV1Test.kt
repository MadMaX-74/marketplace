package ru.otus.todo.app.todo.repo

import org.assertj.core.api.Assertions.assertThat
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import ru.otus.todo.api.v1.models.IResponse
import ru.otus.todo.api.v1.models.TaskCreateRequest
import ru.otus.todo.api.v1.models.TaskDebug
import ru.otus.todo.api.v1.models.TaskDebugMode
import ru.otus.todo.api.v1.models.TaskDeleteRequest
import ru.otus.todo.api.v1.models.TaskListRequest
import ru.otus.todo.api.v1.models.TaskListResponse
import ru.otus.todo.api.v1.models.TaskReadRequest
import ru.otus.todo.api.v1.models.TaskUpdateRequest
import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.models.TodoId
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.common.models.TodoStatus
import ru.otus.todo.mapper.toTransportCreate
import ru.otus.todo.mapper.toTransportDelete
import ru.otus.todo.mapper.toTransportList
import ru.otus.todo.mapper.toTransportRead
import ru.otus.todo.mapper.toTransportUpdate
import ru.otus.todo.stubs.TodoStub
import kotlin.test.Test

internal abstract class TodoRepoBaseV1Test {
    protected abstract var webClient: WebTestClient
    private val debug = TaskDebug(mode = TaskDebugMode.TEST)
    protected val uuidNew = "10000000-0000-0000-0001"

    @Test
    open fun createTodo() {
        val request = TaskCreateRequest(
            requestType = "create",
            task = TodoStub.get().toTransportCreate(),
            debug = debug,
        )

        val expectedResponse = prepareCtx(TodoStub.prepareResult {
            id = TodoId(uuidNew)
        }).toTransportCreate().copy(responseType = "create")

        println("Expected Response: $expectedResponse")

        testRepoTodo("create", request, expectedResponse)
    }


    @Test
    open fun readTodo() = testRepoTodo(
        "read",
        TaskReadRequest(
            task = TodoStub.get().toTransportRead(),
            debug = debug,
        ),
        prepareCtx(TodoStub.get())
            .toTransportRead()
            .copy(responseType = "read")
    )

    @Test
    open fun updateTodo() = testRepoTodo(
        "update",
        TaskUpdateRequest(
            task = TodoStub.prepareResult { title = "add" }.toTransportUpdate(),
            debug = debug,
        ),
        prepareCtx(TodoStub.prepareResult { title = "add" })
            .toTransportUpdate().copy(responseType = "update")
    )

    @Test
    open fun deleteTodo() = testRepoTodo(
        "delete",
        TaskDeleteRequest(
            task = TodoStub.get().toTransportDelete(),
            debug = debug,
        ),
        prepareCtx(TodoStub.get())
            .toTransportDelete()
            .copy(responseType = "delete")
    )

    @Test
    open fun listTodo() = testRepoTodo(
        "list",
        TaskListRequest(
            debug = debug,
        ),
        TodoContext(
            state = TodoState.RUNNING,
            todosResponse = TodoStub.prepareTaskList(TodoStatus.IN_PROGRESS)
                .sortedBy { it.id.asString() }
                .toMutableList()
        )
            .toTransportList().copy(responseType = "search")
    )

    private fun prepareCtx(todo: Todo) = TodoContext(
        state = TodoState.RUNNING,
        todoResponse = todo.apply {
            // Пока не реализована эта функциональность
            //            TODO()
        },
    )

    private inline fun <reified Req : Any, reified Res : IResponse> testRepoTodo(
        url: String,
        requestObj: Req,
        expectObj: Res,
    ) {
        webClient
            .post()
            .uri("/v1/task/$url")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(requestObj))
            .exchange()
            .expectStatus().isOk
            .expectBody(Res::class.java)
            .value {
                println("REQUEST: $requestObj")
                println("RESPONSE: $it")
                val sortedResp: IResponse = when (it) {
                    is TaskListResponse -> it.copy(tasks = it.tasks?.sortedBy { it.id })
                    else -> it
                }
                assertThat(sortedResp).isEqualTo(expectObj)
            }
    }
}
