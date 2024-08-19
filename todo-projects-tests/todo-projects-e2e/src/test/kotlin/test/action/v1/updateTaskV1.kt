package ru.otus.todo.tests.e2e.test.action.v1

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ru.otus.todo.api.v1.models.TaskResponseObject
import ru.otus.todo.api.v1.models.TaskUpdateObject
import ru.otus.todo.api.v1.models.TaskUpdateRequest
import ru.otus.todo.api.v1.models.TaskUpdateResponse
import ru.otus.todo.tests.e2e.fixture.client.Client
import ru.otus.todo.tests.e2e.test.action.beValidId

suspend fun Client.updateTask(task: TaskUpdateObject): TaskResponseObject =
    updateTask(task) {
        it should haveSuccessResult
        it.task shouldNotBe null
        it.task?.apply {
            if (task.title != null)
                task shouldBe task.title
            if (task.description != null)
                description shouldBe task.description
            if (task.status != null)
                status shouldBe task.status
        }
        it.task!!
    }

suspend fun <T> Client.updateTask(task: TaskUpdateObject, block: (TaskUpdateResponse) -> T): T {
    val id = task.id
    return withClue("updatedV1: $id, set: $task") {
        id should beValidId
        val response = sendAndReceive(
            "task/update", TaskUpdateRequest(
                debug = debug,
                task = task.copy(id = id)
            )
        ) as TaskUpdateResponse

        response.asClue(block)
    }
}
