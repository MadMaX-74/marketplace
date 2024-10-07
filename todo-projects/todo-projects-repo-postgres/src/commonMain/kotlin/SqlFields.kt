package ru.otus.todo.repo.postgresql

object SqlFields {
    const val ID = "id"
    const val TITLE = "title"
    const val DESCRIPTION = "description"
    const val STATUS = "status"
    const val CREATED_DATE = "created_date"
    const val COMPLETED_DATE = "completed_date"

    const val STATUS_TYPE = "todo_status_type"
    const val STATUS_NONE = "none"
    const val STATUS_IN_PROGRESS = "in_progress"
    const val STATUS_COMPLETED = "completed"

    const val DELETE_OK = "DELETE_OK"

    fun String.quoted() = "\"$this\""

    val allFields = listOf(
        ID, TITLE, DESCRIPTION, STATUS, CREATED_DATE, COMPLETED_DATE,
    )
}
