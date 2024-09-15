package ru.otus.todo.biz.validation

import TodoContext
import helpers.errorValidation
import helpers.fail
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker


// смотрим пример COR DSL валидации
fun ICorChainDsl<TodoContext>.validateTitleNotEmpty(title: String) = worker {
    this.title = title
    on { todoValidating.title.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "title",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
