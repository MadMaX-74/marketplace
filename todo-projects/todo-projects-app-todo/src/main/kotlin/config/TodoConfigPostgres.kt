package ru.otus.todo.app.todo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import ru.otus.todo.repo.postgresql.SqlProperties

@ConfigurationProperties(prefix = "psql")
class TodoConfigPostgres(var host: String = "localhost",
                         var port: Int = 5433,
                         var user: String = "postgres",
                         var password: String = "todos-pass",
                         var database: String = "todos",
                         var schema: String = "public",
                         var table: String = "todos") {
    val psql: SqlProperties = SqlProperties(
        host = host,
        port = port,
        user = user,
        password = password,
        database = database,
        schema = schema,
        table = table,
    )
}