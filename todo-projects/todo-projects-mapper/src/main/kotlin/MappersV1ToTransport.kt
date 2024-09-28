package ru.otus.todo.mapper

import TodoContext
import exceptions.UnknownTodoCommand
import models.*
import ru.otus.todo.api.v1.models.*


fun TodoContext.toTransportTodo(): IResponse = when (val cmd = command) {
    TodoCommand.CREATE -> toTransportCreate()
    TodoCommand.READ -> toTransportRead()
    TodoCommand.UPDATE -> toTransportUpdate()
    TodoCommand.DELETE -> toTransportDelete()
    TodoCommand.LIST -> toTransportList()
    TodoCommand.NONE -> throw UnknownTodoCommand(cmd)
    TodoCommand.REPORT -> TODO()
}

fun TodoContext.toTransportCreate() = TaskCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    task = todoResponse.toTransportTask()
)

fun TodoContext.toTransportRead() = TaskReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    task = todoResponse.toTransportTask()
)

fun TodoContext.toTransportUpdate() = TaskUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    task = todoResponse.toTransportTask()
)

fun TodoContext.toTransportDelete() = TaskDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
)

fun TodoContext.toTransportList() = TaskListResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    tasks = todosResponse.toTransportTasks()
)


fun List<Todo>.toTransportTasks(): List<TaskResponseObject>? = this
    .map { it.toTransportTask() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun Todo.toTransportTask(): TaskResponseObject = TaskResponseObject(
    id = if (id != TodoId.NONE) id.asString() else null,
    title = title.takeIf { it.isNotBlank() == true },
    description = description.takeIf { it.isNotBlank() == true },
    status = status.toTransportTask()
)


private fun TodoStatus.toTransportTask(): TaskStatus? = when (this) {
    TodoStatus.IN_PROGRESS -> TaskStatus.IN_PROGRESS
    TodoStatus.COMPLETED -> TaskStatus.COMPLETED
    TodoStatus.NONE -> null
}

private fun List<TodoError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportError() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun TodoError.toTransportError() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() }
)

private fun TodoState.toResult(): ResponseResult? = when (this) {
    TodoState.RUNNING -> ResponseResult.SUCCESS
    TodoState.FAILING -> ResponseResult.ERROR
    TodoState.FINISHING -> ResponseResult.SUCCESS
    TodoState.NONE -> null
}
