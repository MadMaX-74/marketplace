package ru.otus.todo.common.exceptions

import ru.otus.todo.common.models.TodoCommand


class UnknownTodoCommand(command: TodoCommand) : Throwable("Wrong command $command at mapping toTransport stage")
