package ru.otus.todo.app.todo.controllers

import controllerHelper
import org.springframework.web.bind.annotation.*
import ru.otus.todo.api.v1.models.*
import ru.otus.todo.app.todo.config.TodoAppSettings
import ru.otus.todo.mapper.fromTransport
import ru.otus.todo.mapper.toTransportTodo
import kotlin.reflect.KClass

@Suppress("unused")
@RestController
@RequestMapping("v1/task")
class TodoControllerV1(
    private val appSettings: TodoAppSettings
) {

    @PostMapping("create")
    suspend fun create(@RequestBody request: TaskCreateRequest): TaskCreateResponse =
        process(appSettings, request = request, this::class, "create")

    @PostMapping("read")
    suspend fun  read(@RequestBody request: TaskReadRequest): TaskReadResponse =
        process(appSettings, request = request, this::class, "read")

    @RequestMapping("update", method = [RequestMethod.POST])
    suspend fun  update(@RequestBody request: TaskUpdateRequest): TaskUpdateResponse =
        process(appSettings, request = request, this::class, "update")

    @PostMapping("delete")
    suspend fun  delete(@RequestBody request: TaskDeleteRequest): TaskDeleteResponse =
        process(appSettings, request = request, this::class, "delete")


    @PostMapping("list")
    suspend fun  offers(@RequestBody request: TaskListRequest): TaskListResponse =
        process(appSettings, request = request, this::class, "list")

    companion object {
        suspend inline fun <reified Q : IRequest, reified R : IResponse> process(
            appSettings: TodoAppSettings,
            request: Q,
            clazz: KClass<*>,
            logId: String,
        ): R = appSettings.controllerHelper(
            { fromTransport(request) },
            { toTransportTodo() as R },
            clazz,
            logId
        )
    }
}
