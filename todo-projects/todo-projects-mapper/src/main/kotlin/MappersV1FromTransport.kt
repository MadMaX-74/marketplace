package ru.otus.todo.mapper

import TodoContext
import models.*
import ru.otus.otuskotlin.marketplace.mappers.v1.exceptions.UnknownRequestClass
import ru.otus.todo.api.v1.models.*
import stubs.TodoStubs


fun TodoContext.fromTransport(request: IRequest) = when (request) {
    is TaskCreateRequest -> fromTransport(request)
    is TaskReadRequest -> fromTransport(request)
    is TaskUpdateRequest -> fromTransport(request)
    is TaskDeleteRequest -> fromTransport(request)
    is TaskListRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun TaskDebug?.transportToWorkMode(): TodoWorkMode = when (this?.mode) {
    TaskDebugMode.PROD -> TodoWorkMode.PROD
    TaskDebugMode.TEST -> TodoWorkMode.TEST
    TaskDebugMode.STUB -> TodoWorkMode.STUB
    null -> TodoWorkMode.PROD
}

private fun TaskDebug?.transportToStubCase(): TodoStubs = when (this?.stub) {
    TaskRequestDebugStubs.SUCCESS -> TodoStubs.SUCCESS
    TaskRequestDebugStubs.NOT_FOUND -> TodoStubs.NOT_FOUND
    TaskRequestDebugStubs.BAD_ID -> TodoStubs.BAD_ID
    TaskRequestDebugStubs.BAD_TITLE -> TodoStubs.BAD_TITLE
    TaskRequestDebugStubs.BAD_DESCRIPTION -> TodoStubs.BAD_DESCRIPTION
    TaskRequestDebugStubs.CANNOT_DELETE -> TodoStubs.CANNOT_DELETE
    null -> TodoStubs.NONE
}

private fun TodoContext.fromTransport(request: TaskCreateRequest) {
    command = TodoCommand.CREATE
    todoRequest = request.task?.toInternal() ?: Todo()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun TodoContext.fromTransport(request: TaskReadRequest) {
    command = TodoCommand.READ
    todoRequest = request.id.toTaskWithId()
    workMode = request.debug?.transportToWorkMode() ?: TodoWorkMode.PROD
    stubCase = request.debug?.transportToStubCase() ?: TodoStubs.NONE
}


private fun TodoContext.fromTransport(request: TaskUpdateRequest) {
    command = TodoCommand.UPDATE
    todoRequest = request.task?.toInternal() ?: Todo()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun TodoContext.fromTransport(request: TaskDeleteRequest) {
    command = TodoCommand.DELETE
    todoRequest = request.id.toTaskWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun TodoContext.fromTransport(request: TaskListRequest) {
    command = TodoCommand.LIST
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

// Дополнительные функции для преобразования типов
private fun String?.toTaskId(): TodoId = this?.let { TodoId(it) } ?: TodoId.NONE
private fun String?.toTaskWithId(): Todo = Todo(id = this.toTaskId().asString())

private fun TaskCreateObject.toInternal(): Todo = Todo(
    title = this.title ?: "",
    description = this.description ?: "",
    status = this.status?.toInternal() ?: TodoStatus.NONE
)

private fun TaskUpdateObject.toInternal(): Todo = Todo(
    id = this.id.toTaskId().toString(),
    title = this.title ?: "",
    description = this.description ?: "",
    status = this.status?.toInternal() ?: TodoStatus.NONE
)


private fun TaskStatus.toInternal(): TodoStatus = when (this) {
    TaskStatus.IN_PROGRESS -> TodoStatus.IN_PROGRESS
    TaskStatus.COMPLETED -> TodoStatus.COMPLETED
    null -> TodoStatus.NONE
}
