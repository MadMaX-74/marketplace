package ru.otus.todo.repo.inmemory

import kotlinx.datetime.Instant
import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.models.TodoId
import ru.otus.todo.common.models.TodoStatus

data class TodoEntity(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    var status: TodoStatus = TodoStatus.NONE,
    var createdDate: String? = null,
    var completedDate: String? = null
) {
    constructor(model: Todo) : this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        title = model.title.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        status = model.status,
        createdDate = model.createdDate,
        completedDate = model.completedDate
    )

    fun toInternal() = Todo(
        id = id?.let { TodoId(it) } ?: TodoId.NONE,
        title = title ?: "",
        description = description ?: "",
        status = status,
        createdDate = createdDate,
        completedDate = completedDate
    )
}
