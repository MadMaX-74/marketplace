package models

import kotlinx.datetime.Instant

data class Todo(
    var id: String = TodoId.NONE.asString(),
    var title: String = "",
    var description: String = "",
    var status: TodoStatus = TodoStatus.NONE,
    var createdDate: Instant = Instant.DISTANT_PAST,
    var completedDate: Instant? = null
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = Todo()
    }
}
