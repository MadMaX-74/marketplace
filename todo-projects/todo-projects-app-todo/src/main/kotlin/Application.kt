package ru.otus.todo.app.todo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class Application


// swagger URL: http://localhost:8080/swagger-ui.html

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
