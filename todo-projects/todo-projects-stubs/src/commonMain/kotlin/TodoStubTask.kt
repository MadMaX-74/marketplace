package ru.otus.otuskotlin.marketplace.stubs

import kotlinx.datetime.Instant
import models.Todo
import models.TodoStatus


object TodoStubTask {
    val TASK_IN_PROGRESS: Todo
        get() = Todo(
            id = 1.toString(),
            title = "title1",
            description = "description1",
            status = TodoStatus.IN_PROGRESS,
            createdDate = Instant.DISTANT_PAST,
            completedDate = null
        )
    val TASK_COMPLETED = TASK_IN_PROGRESS.copy(status = TodoStatus.COMPLETED)
}
