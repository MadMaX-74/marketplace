package ru.otus.todo.common.models


data class Todo(
    var id: TodoId = TodoId.NONE,
    var title: String = "",
    var description: String = "",
    var status: TodoStatus = TodoStatus.NONE,
    var createdDate: String? = null,
    var completedDate: String? = null
) {
    fun deepCopy(): Todo = copy()
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = Todo()
    }
}
