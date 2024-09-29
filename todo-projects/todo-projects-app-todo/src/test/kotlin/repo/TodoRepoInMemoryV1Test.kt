package ru.otus.todo.app.todo.repo

import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import ru.otus.todo.app.todo.config.TodoConfig
import ru.otus.todo.app.todo.controllers.TodoControllerV1
import ru.otus.todo.common.repo.IRepoTodo
import kotlin.test.Test
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.slot
import org.springframework.boot.test.mock.mockito.MockBean
import ru.otus.todo.app.todo.services.RabbitMQSender
import ru.otus.todo.common.models.TodoStatus
import ru.otus.todo.common.repo.DbTodoIdRequest
import ru.otus.todo.common.repo.DbTodoListRequest
import ru.otus.todo.common.repo.DbTodoRequest
import ru.otus.todo.repo.common.TodoRepoInitialized
import ru.otus.todo.repo.inmemory.TodoRepoInMemory
import ru.otus.todo.stubs.TodoStub

// Temporary simple test with stubs
@WebFluxTest(TodoControllerV1::class, TodoConfig::class)
internal class TodoRepoInMemoryV1Test : TodoRepoBaseV1Test() {
    @Autowired
    override lateinit var webClient: WebTestClient

    @MockkBean
    @Qualifier("testRepo")
    lateinit var testTestRepo: IRepoTodo

    @MockBean
    lateinit var rabbitMQSender: RabbitMQSender

    @BeforeEach
    fun tearUp() {
        val slotTodo = slot<DbTodoRequest>()
        val slotId = slot<DbTodoIdRequest>()
        val slotLs = slot<DbTodoListRequest>()
        val repo = TodoRepoInitialized(
            repo = TodoRepoInMemory(randomUuid = { uuidNew }),
            initObjects = TodoStub.prepareTaskList(TodoStatus.IN_PROGRESS) + TodoStub.get()
        )
        coEvery { testTestRepo.createTodo(capture(slotTodo)) } coAnswers { repo.createTodo(slotTodo.captured) }
        coEvery { testTestRepo.readTodo(capture(slotId)) } coAnswers { repo.readTodo(slotId.captured) }
        coEvery { testTestRepo.updateTodo(capture(slotTodo)) } coAnswers { repo.updateTodo(slotTodo.captured) }
        coEvery { testTestRepo.deleteTodo(capture(slotId)) } coAnswers { repo.deleteTodo(slotId.captured) }
        coEvery { testTestRepo.listTodo(capture(slotLs)) } coAnswers { repo.listTodo(slotLs.captured) }
    }

    @Test
    override fun deleteTodo() = super.deleteTodo()
}
