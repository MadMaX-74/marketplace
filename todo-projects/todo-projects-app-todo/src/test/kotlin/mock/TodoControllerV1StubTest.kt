package ru.otus.todo.app.todo.mock

import TodoContext
import models.TodoState
import models.TodoStatus
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import ru.otus.todo.api.v1.models.*
import ru.otus.todo.app.todo.config.TodoConfig
import ru.otus.todo.app.todo.controllers.TodoControllerV1
import ru.otus.todo.app.todo.services.RabbitMQSender
import ru.otus.todo.mapper.*
import ru.otus.todo.stubs.TodoStub
import kotlin.test.Test

// Temporary simple test with stubs
@WebFluxTest(TodoControllerV1::class, TodoConfig::class)
internal class TodoControllerV1StubTest {
    @Autowired
    private lateinit var webClient: WebTestClient

    @MockBean
    lateinit var rabbitMQSender: RabbitMQSender

    @Test
    fun createTask() = testStubTask(
        "/v1/task/create",
        TaskCreateRequest(),
        TodoContext(todoResponse = TodoStub.get(), state = TodoState.FINISHING)
            .toTransportCreate().copy(responseType = "create")
    )

    @Test
    fun readTask() = testStubTask(
        "/v1/task/read",
        TaskReadRequest(),
        TodoContext(todoResponse = TodoStub.get(), state = TodoState.FINISHING)
            .toTransportRead().copy(responseType = "read")
    )

    @Test
    fun updateTask() = testStubTask(
        "/v1/task/update",
        TaskUpdateRequest(),
        TodoContext(todoResponse = TodoStub.get(), state = TodoState.FINISHING)
            .toTransportUpdate().copy(responseType = "update")
    )

    @Test
    fun deleteTask() = testStubTask(
        "/v1/task/delete",
        TaskDeleteRequest(),
        TodoContext(todoResponse = TodoStub.get(), state = TodoState.FINISHING)
            .toTransportDelete().copy(responseType = "delete")
    )

    @Test
    fun listTasks() = testStubTask(
        "/v1/task/list",
        TaskListRequest(),
        TodoContext(todosResponse = TodoStub.prepareTaskList(TodoStatus.IN_PROGRESS).toMutableList(), state = TodoState.FINISHING)
            .toTransportList().copy(responseType = "list")
    )

    private inline fun <reified Req : Any, reified Res : Any> testStubTask(
        url: String,
        requestObj: Req,
        responseObj: Res,
    ) {
        webClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(requestObj))
            .exchange()
            .expectStatus().isOk
            .expectBody(Res::class.java)
            .value {
                println("RESPONSE: $it")
                assertThat(it).isEqualTo(responseObj)
            }
    }
}
