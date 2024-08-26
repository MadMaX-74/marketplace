package ru.otus.todo.stubs

import kotlinx.datetime.Clock
import models.Todo
import models.TodoId
import models.TodoStatus
import ru.otus.todo.stubs.TodoStubTask.TASK_IN_PROGRESS


object TodoStub {
    fun get() : Todo = TASK_IN_PROGRESS.copy()

    fun prepareResult(block: Todo.() -> Unit): Todo = get().apply { block() }

    fun prepareTaskList(status: TodoStatus) = listOf(
        todoTask("task-001", status),
        todoTask("task-002", status),
        todoTask("task-003", status)
    )

    private fun todoTask(id: String, status: TodoStatus) = Todo(
        id = TodoId(id).toString(),
        title = "Task $id",
        description = "Description of task $id",
        status = status,
        createdDate = Clock.System.now(),
        completedDate = if (status == TodoStatus.COMPLETED) Clock.System.now() else null
    )
}
