package ru.otus.todo.tests.e2e.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldNotBe
import ru.otus.todo.api.v1.models.TaskReadObject
import ru.otus.todo.api.v1.models.TaskReadRequest
import ru.otus.todo.api.v1.models.TaskReadResponse
import ru.otus.todo.api.v1.models.TaskResponseObject
import ru.otus.todo.tests.e2e.test.action.beValidId
import ru.otus.todo.tests.e2e.fixture.client.Client

suspend fun Client.readTask(id: String?): TaskResponseObject = readTask(id) {
    it should haveSuccessResult
    it.task shouldNotBe null
    it.task!!
}

suspend fun <T> Client.readTask(id: String?, block: (TaskReadResponse) -> T): T =
    withClue("readTaskV1: $id") {
        id should beValidId
        val response = sendAndReceive(
            "v1",
            "task/read",
            TaskReadRequest(
                requestType = "read",
                debug = debugStubV1,
                task = TaskReadObject(id = id)
            ).toString()
        ) as TaskReadResponse

        response.asClue(block)
    }
