package exceptions

import models.TodoCommand

class UnknownTodoCommand(command: TodoCommand) : Throwable("Wrong command $command at mapping toTransport stage")
