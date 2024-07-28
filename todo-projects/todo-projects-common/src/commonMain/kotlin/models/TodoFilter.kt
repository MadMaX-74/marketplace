package models

data class TodoFilter(
    var searchString: String = "",
    var status: TodoStatus = TodoStatus.NONE
)
