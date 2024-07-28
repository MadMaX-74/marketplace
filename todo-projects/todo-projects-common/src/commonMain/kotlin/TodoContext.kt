import models.TodoCommand


data class TodoContext(
    var command: TodoCommand = TodoCommand.NONE

    )
