package ru.otus.todo.tests.e2e.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import ru.otus.todo.api.v1.models.TaskListRequest
import ru.otus.todo.api.v1.models.TaskListResponse
import ru.otus.todo.tests.e2e.fixture.client.Client

suspend fun Client.listTask(): TaskListResponse = listTask() {
    it should haveSuccessResult
    it
}

suspend fun <T> Client.listTask(block: (TaskListResponse) -> T): T =
    withClue("taskListV1") {
        val response = sendAndReceive(
            "v1",
            "task/list",
            TaskListRequest(
                debug = debugStubV1,
            ).toString()
        ) as TaskListResponse

        response.asClue(block)
    }
