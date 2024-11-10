package ru.otus.todo.app.todo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.otus.todo.app.todo.config.TodoConfigPostgres

@SpringBootTest
class ApplicationTests {

    @Autowired
    var pgConf: TodoConfigPostgres= TodoConfigPostgres()

    @Test
    fun contextLoads() {
        assertEquals(5433, pgConf.psql.port)
        assertEquals("todos", pgConf.psql.database)
    }
}
