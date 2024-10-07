package ru.otus.todo.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class TodoRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = TodoRequestId("")
    }
}
