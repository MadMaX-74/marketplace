package ru.otus.todo.tests.e2e.test

import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import ru.otus.todo.tests.e2e.test.action.v1.readTask
import ru.otus.todo.api.v1.models.TaskDebug
import ru.otus.todo.api.v1.models.TaskResponseObject
import ru.otus.todo.api.v1.models.TaskStatus
import ru.otus.todo.api.v1.models.TaskUpdateObject
import ru.otus.todo.tests.e2e.fixture.client.Client
import ru.otus.todo.tests.e2e.test.action.v1.*

fun FunSpec.testApiV1(client: Client, prefix: String = "", debug: TaskDebug = debugStubV1) {
    context("${prefix}v1") {
        test("Create Task ok") {
            client.createTask()
        }

        test("Read Task ok") {
            val created = client.createTask()
            client.readTask(created.id).asClue {
                it shouldBe created
            }
        }

        test("Update Task ok") {
            val created = client.createTask()
            val updateAd = TaskUpdateObject(
                id = created.id,
                title = "Updated Title",
                description = created.description,
                status = TaskStatus.IN_PROGRESS
            )
            client.updateTask(updateAd)
        }

        test("Delete Task ok") {
            val created = client.createTask()
            client.deleteTask(created)
        }


        test("List Task ok") {
            val task1 = client.createTask(someCreateTask.copy(title = "New Task"))
            val task2 = client.createTask(someCreateTask.copy(title = "New new task"))

            val taskListResponse = client.listTask()

            withClue("Return task list") {
                taskListResponse.tasks shouldContainExactly listOf(
                    TaskResponseObject(
                        id = task1.id,
                        title = "New Task",
                        description = "Task description",
                        status = task1.status,
                        createdDate = task1.createdDate,
                        completedDate = task1.completedDate
                    ),
                    TaskResponseObject(
                        id = task2.id,
                        title = "New new task",
                        description = "Task description",
                        status = task2.status,
                        createdDate = task2.createdDate,
                        completedDate = task2.completedDate
                    )
                )
            }
        }
    }

}
