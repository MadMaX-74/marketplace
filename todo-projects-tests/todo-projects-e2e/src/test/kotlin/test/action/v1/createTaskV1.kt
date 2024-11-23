package ru.otus.todo.tests.e2e.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ru.otus.todo.api.v1.models.*
import ru.otus.todo.tests.e2e.fixture.client.Client

suspend fun Client.createTask(task: TaskCreateObject = someCreateTask): TaskResponseObject = createTask(task) {
    it should haveSuccessResult
    it.task shouldNotBe null
    it.task?.apply {
        title shouldBe task.title
        description shouldBe task.description
        status shouldBe task.status
    }
    it.task!!
}

suspend fun <T> Client.createTask(task: TaskCreateObject = someCreateTask, block: (TaskCreateResponse) -> T): T =
    withClue("createTaskV1: $task") {
        val response = sendAndReceive(
            "task/create", TaskCreateRequest(
                requestType = "create",
                debug = debugStubV1,
                task = task
            )
        ) as TaskCreateResponse

        response.asClue(block)
    }
