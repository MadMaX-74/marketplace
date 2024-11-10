package ru.otus.todo.app.todo.controllers

import org.springframework.web.bind.annotation.*
import ru.otus.todo.api.v1.models.*
import ru.otus.todo.app.common.controllerHelper
import ru.otus.todo.app.todo.config.TodoAppSettings
import ru.otus.todo.app.todo.services.RabbitMQSender
import ru.otus.todo.app.todo.services.TaskMessage
import ru.otus.todo.app.todo.services.toFormattedString
import ru.otus.todo.mapper.fromTransport
import ru.otus.todo.mapper.toTransportTodo
import java.time.LocalDateTime

@RestController
@RequestMapping("v1/task")
class TodoControllerV1(
    private val appSettings: TodoAppSettings,
    private val rabbitMQSender: RabbitMQSender
) {

    @PostMapping("create")
    suspend fun create(@RequestBody request: TaskCreateRequest): TaskCreateResponse {
        val response: TaskCreateResponse = process(appSettings, request = request)

        val taskMessage = TaskMessage(
            date = LocalDateTime.now().toFormattedString(),
            operation = "CREATE",
            title = request.task?.title ?: "Default Title",
            description = request.task?.description ?: "Default Description",
            status = (response.task?.status ?: "Default Status").toString()
        )
        rabbitMQSender.sendTaskMessage(taskMessage)

        return response
    }

    @PostMapping("read")
    suspend fun  read(@RequestBody request: TaskReadRequest): TaskReadResponse =
        process(appSettings, request = request)

    @RequestMapping("update", method = [RequestMethod.POST])
    suspend fun  update(@RequestBody request: TaskUpdateRequest): TaskUpdateResponse {
        val response: TaskUpdateResponse = process(appSettings, request = request)

        val taskMessage = TaskMessage(
            date = LocalDateTime.now().toFormattedString(),
            operation = "UPDATE",
            title = request.task?.title ?: "Default Title",
            description = request.task?.description ?: "Default Description",
            status = (response.task?.status ?: "Default Status").toString()
        )
        rabbitMQSender.sendTaskMessage(taskMessage)

        return response
    }

    @PostMapping("delete")
    suspend fun  delete(@RequestBody request: TaskDeleteRequest): TaskDeleteResponse {
        val response: TaskDeleteResponse = process(appSettings, request = request)

        val taskMessage = TaskMessage(
            date = LocalDateTime.now().toFormattedString(),
            operation = "DELETE",
            title = request.task?.title ?: "Default Title",
            description = request.task?.description ?: "Default Description",
            status = "Deleted"
        )
        rabbitMQSender.sendTaskMessage(taskMessage)

        return response
    }


    @PostMapping("list")
    suspend fun list(@RequestBody request: TaskListRequest): TaskListResponse {
        println("Incoming request: $request")
        return process(appSettings, request = request)
    }

    companion object {
        suspend inline fun <reified Q : IRequest, reified R : IResponse> process(
            appSettings: TodoAppSettings,
            request: Q,
        ): R = appSettings.controllerHelper(
            { fromTransport(request) },
            { toTransportTodo() as R }
        )
    }
}
