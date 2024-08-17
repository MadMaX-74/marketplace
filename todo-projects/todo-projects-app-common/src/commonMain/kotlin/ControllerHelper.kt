import kotlinx.datetime.Clock
import models.TodoCommand
import models.TodoState
import ru.otus.otuskotlin.marketplace.common.helpers.asTodoError
import ru.otus.todo.app.common.TodoAppSettings

suspend inline fun <T> TodoAppSettings.controllerHelper(
    crossinline getRequest: suspend TodoContext.() -> Unit,
    crossinline toResponse: suspend TodoContext.() -> T,
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
