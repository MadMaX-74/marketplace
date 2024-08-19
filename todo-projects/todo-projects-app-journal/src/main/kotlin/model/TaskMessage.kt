package ru.otus.todo.app.journal.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class TaskMessage(
    val date: LocalDateTime,
    val operation: String,
    val title: String,
    val description: String,
    val status: String
)
