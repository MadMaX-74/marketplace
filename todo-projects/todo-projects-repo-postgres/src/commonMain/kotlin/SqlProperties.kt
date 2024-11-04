package ru.otus.todo.repo.postgresql

data class SqlProperties(
    val host: String = "localhost",
    val port: Int = 5433,
    val user: String = "postgres",
    val password: String = "todos-pass",
    val database: String = "todos_ads",
    val schema: String = "public",
    val table: String = "todos",
) {
    val url: String
        get() = "jdbc:postgresql://${host}:${port}/${database}"
}
