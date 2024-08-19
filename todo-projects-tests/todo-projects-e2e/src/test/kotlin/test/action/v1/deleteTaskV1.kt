package ru.otus.todo.tests.e2e.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import ru.otus.todo.api.v1.models.TaskDeleteObject
import ru.otus.todo.api.v1.models.TaskDeleteRequest
import ru.otus.todo.api.v1.models.TaskDeleteResponse
import ru.otus.todo.api.v1.models.TaskResponseObject
import ru.otus.todo.tests.e2e.fixture.client.Client
import ru.otus.todo.tests.e2e.test.action.beValidId

suspend fun Client.deleteTask(task: TaskResponseObject) {
    val id = task.id
    withClue("deleteAdV2: $id") {
        id should beValidId
        val response = sendAndReceive(
            "ad/delete",
            TaskDeleteRequest(
                debug = debug,
                task = TaskDeleteObject(id = id)
            )
        ) as TaskDeleteResponse

        response.asClue {
            response should haveSuccessResult
        }
    }
}
