package ru.otus.todo.app.journal

import ru.otus.todo.app.journal.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.cio.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureRouting()

    // Start RabbitMQ consumer
    RabbitMQConsumer.start()
}

