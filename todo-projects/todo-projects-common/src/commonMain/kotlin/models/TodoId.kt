package models

import kotlin.jvm.JvmInline

@JvmInline
value class TodoId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = TodoId("")
    }
}
