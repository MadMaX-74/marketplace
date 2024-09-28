package ru.otus.todo.mapper


import ru.otus.todo.api.v1.models.TaskCreateObject
import ru.otus.todo.api.v1.models.TaskDeleteObject
import ru.otus.todo.api.v1.models.TaskReadObject
import ru.otus.todo.api.v1.models.TaskStatus
import ru.otus.todo.api.v1.models.TaskUpdateObject
import ru.otus.todo.common.models.*


fun Todo.toTransportCreate() = TaskCreateObject(
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() }
)

fun Todo.toTransportRead() = TaskReadObject(
    id = id.takeIf { it != TodoId.NONE }?.asString()
)

fun Todo.toTransportUpdate() = TaskUpdateObject(
    id = id.takeIf { it != TodoId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    status = status.toTransportStatus() // Добавляем статус задачи
)

fun Todo.toTransportDelete() = TaskDeleteObject(
    id = id.takeIf { it != TodoId.NONE }?.asString()
)

private fun TodoStatus.toTransportStatus(): TaskStatus? = when (this) {
    TodoStatus.IN_PROGRESS -> TaskStatus.IN_PROGRESS
    TodoStatus.COMPLETED -> TaskStatus.COMPLETED
    TodoStatus.NONE -> null
}