package ru.otus.todo.app.common


import kotlinx.datetime.Clock
import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.helpers.asTodoError
import ru.otus.todo.common.models.TodoCommand
import ru.otus.todo.common.models.TodoState

suspend inline fun <T> TodoAppSettings.controllerHelper(
    crossinline getRequest: suspend TodoContext.() -> Unit,
    crossinline toResponse: suspend TodoContext.() -> T
): T {
    val ctx = TodoContext(
        timeStart = Clock.System.now(),
    )
    return try {
        ctx.getRequest()
        processor.exec(ctx)
        ctx.toResponse()
    } catch (e: Throwable) {
        ctx.state = TodoState.FAILING
        ctx.errors.add(e.asTodoError())
        processor.exec(ctx)
        if (ctx.command == TodoCommand.NONE) {
            ctx.command = TodoCommand.READ
        }
        ctx.toResponse()
    }
}
