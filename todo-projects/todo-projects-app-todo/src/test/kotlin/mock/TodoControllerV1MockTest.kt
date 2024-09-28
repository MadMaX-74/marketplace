package ru.otus.todo.app.todo.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.mockito.kotlin.any
import org.mockito.kotlin.wheneverBlocking
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
import ru.otus.todo.biz.TodoProcessor
import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.models.TodoStatus
import ru.otus.todo.mapper.*
import ru.otus.todo.stubs.TodoStub
import kotlin.test.Test

@WebFluxTest(TodoControllerV1::class, TodoConfig::class)
internal class TodoControllerV1MockTest {
    @Autowired
    private lateinit var webClient: WebTestClient

    @Suppress("unused")
    @MockBean
    private lateinit var processor: TodoProcessor

    @MockBean
    lateinit var rabbitMQSender: RabbitMQSender


    @BeforeEach
    fun tearUp() {
        wheneverBlocking { processor.exec(any()) }.then {
            it.getArgument<TodoContext>(0).apply {
                todoResponse = TodoStub.get()
                todosResponse = TodoStub.prepareTaskList(TodoStatus.IN_PROGRESS).toMutableList()
            }
        }
    }

    @Test
    fun createTask() = testStubTask(
        "/v1/task/create",
        TaskCreateRequest(),
        TodoContext(todoResponse = TodoStub.get()).toTransportCreate().copy(responseType = "create")
    )

    @Test
    fun readTask() = testStubTask(
        "/v1/task/read",
        TaskReadRequest(),
        TodoContext(todoResponse = TodoStub.get()).toTransportRead().copy(responseType = "read")
    )

    @Test
    fun updateTask() = testStubTask(
        "/v1/task/update",
        TaskUpdateRequest(),
        TodoContext(todoResponse = TodoStub.get()).toTransportUpdate().copy(responseType = "update")
    )

    @Test
    fun deleteTask() = testStubTask(
        "/v1/task/delete",
        TaskDeleteRequest(),
        TodoContext(todoResponse = TodoStub.get()).toTransportDelete().copy(responseType = "delete")
    )

    @Test
    fun listTasks() = testStubTask(
        "/v1/task/list",
        TaskListRequest(),
        TodoContext(todosResponse = TodoStub.prepareTaskList(TodoStatus.IN_PROGRESS).toMutableList())
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
