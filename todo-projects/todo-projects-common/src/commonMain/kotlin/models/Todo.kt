package models

import kotlinx.datetime.Instant

data class Todo(
    var id: TodoId = TodoId.NONE,
    var title: String = "",
    var description: String = "",
    var status: TodoStatus = TodoStatus.NONE,
    var createdDate: Instant = Instant.DISTANT_PAST,
    var completedDate: Instant? = null
) {
    fun deepCopy(): Todo = copy()
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = Todo()
    }
}
