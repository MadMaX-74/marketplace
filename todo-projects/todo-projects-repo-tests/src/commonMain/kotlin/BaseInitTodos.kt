package ru.otus.todo.repo.tests

import kotlinx.datetime.Instant
import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.models.TodoId
import ru.otus.todo.common.models.TodoStatus


abstract class BaseInitTodos(private val op: String): IInitObjects<Todo> {
    fun createInitTestModel(suf: String): Todo =
        Todo(
            id = TodoId("todo-${suf}-${op}"),
            title = "title-${suf}-${op}",
            description = "description-${suf}-${op}",
            status = TodoStatus.COMPLETED,
            createdDate = Instant.DISTANT_PAST,
            completedDate = Instant.DISTANT_FUTURE,
        )
}
