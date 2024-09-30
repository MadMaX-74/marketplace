package ru.otus.todo.repo.postgresql

import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject
import ru.otus.todo.common.models.TodoStatus

fun Table.statusEnumeration(
    columnName: String
) = customEnumeration(
    name = columnName,
    sql = SqlFields.STATUS_TYPE,
    fromDb = { value ->
        when (value.toString()) {
            SqlFields.STATUS_IN_PROGRESS -> TodoStatus.IN_PROGRESS
            SqlFields.STATUS_COMPLETED -> TodoStatus.COMPLETED
            else -> TodoStatus.NONE
        }
    },
    toDb = { value ->
        when (value) {
            TodoStatus.IN_PROGRESS -> PgStatusInProgress
            TodoStatus.COMPLETED -> PgStatusCompleted
            TodoStatus.NONE -> throw Exception("Wrong value of Status. NONE is unsupported")
        }
    }
)

sealed class PgStatusValue(eValue: String) : PGobject() {
    init {
        type = SqlFields.STATUS_TYPE
        value = eValue
    }
}


object PgStatusInProgress : PgStatusValue(SqlFields.STATUS_IN_PROGRESS) {
    private fun readResolve(): Any = PgStatusInProgress
}

object PgStatusCompleted : PgStatusValue(SqlFields.STATUS_COMPLETED) {
    private fun readResolve(): Any = PgStatusCompleted
}
