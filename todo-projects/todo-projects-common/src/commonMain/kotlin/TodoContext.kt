package ru.otus.todo.common


import kotlinx.datetime.Instant
import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.models.TodoCommand
import ru.otus.todo.common.models.TodoError
import ru.otus.todo.common.models.TodoRequestId
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.common.models.TodoWorkMode
import ru.otus.todo.common.repo.IRepoTodo
import ru.otus.todo.common.stubs.TodoStubs


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
    var todosRequest: List<Todo> = mutableListOf(),

    var todoRepo: IRepoTodo = IRepoTodo.NONE,
    var todoRepoRead: Todo = Todo(),
    var todoRepoPrepare: Todo = Todo(),
    var todoRepoDone: Todo = Todo(),
    var todosRepoDone: MutableList<Todo> = mutableListOf(),

    var todoResponse: Todo = Todo(),
    var todosResponse: MutableList<Todo> = mutableListOf()

    )
