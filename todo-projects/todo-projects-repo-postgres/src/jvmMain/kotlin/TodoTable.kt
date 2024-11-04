package ru.otus.todo.repo.postgresql

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.models.TodoId
import ru.otus.todo.common.models.TodoStatus
import ru.otus.todo.repo.postgresql.SqlFields.COMPLETED_DATE
import ru.otus.todo.repo.postgresql.SqlFields.CREATED_DATE


class TodoTable(tableName: String) : Table(tableName) {
    val id = text(SqlFields.ID)
    val title = text(SqlFields.TITLE).nullable()
    val description = text(SqlFields.DESCRIPTION).nullable()
    val status = statusEnumeration(SqlFields.STATUS)
    val createdDate = text(CREATED_DATE)
    val completedDate = text(COMPLETED_DATE).nullable()

    override val primaryKey = PrimaryKey(id)

    fun from(res: ResultRow) = Todo(
        id = TodoId(res[id].toString()),
        title = res[title] ?: "",
        description = res[description] ?: "",
        status = res[status],
        createdDate = res[createdDate].toString(),
        completedDate = res[completedDate]?.toString()
    )

    fun to(it: UpdateBuilder<*>, todo: Todo, randomUuid: () -> String) {
        it[id] = todo.id.takeIf { it != TodoId.NONE }?.asString() ?: randomUuid()
        it[title] = todo.title
        it[description] = todo.description
        it[status] = todo.status
        it[createdDate] = todo.createdDate.toString()
        it[completedDate] = todo.completedDate
    }




}
