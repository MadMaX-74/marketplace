import kotlinx.datetime.Instant
import models.*
import stubs.TodoStubs


data class TodoContext(
    var command: TodoCommand = TodoCommand.NONE,
    var state: TodoState = TodoState.NONE,
    val errors: MutableList<TodoError> = mutableListOf(),

    var corSettings: TodoCorSettings = TodoCorSettings(),
    var workMode: TodoWorkMode = TodoWorkMode.PROD,
    var stubCase: TodoStubs = TodoStubs.NONE,

    var todoValidating: Todo = Todo(),
    var todoValidated: Todo = Todo(),

    var requestId: TodoRequestId = TodoRequestId.NONE,
    var timeStart: Instant = Instant.DISTANT_PAST,
    var todoRequest: Todo = Todo(),
    var todoFilterRequest: TodoFilter = TodoFilter(),

    var todoResponse: Todo = Todo(),
    var todosResponse: MutableList<Todo> = mutableListOf()

    )
