package ru.otus.todo.app.todo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import ru.otus.todo.repo.postgresql.SqlProperties


@ConfigurationProperties(prefix = "psql")
class TodoConfigPostgres(var host: String = "localhost",
                         var port: Int = 5432,
                         var user: String = "postgres",
                         var password: String = "todos-pass",
                         var database: String = "todos_ads",
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