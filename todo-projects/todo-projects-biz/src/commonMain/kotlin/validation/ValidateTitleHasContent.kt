package ru.otus.todo.biz.validation


import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.helpers.errorValidation
import ru.otus.todo.common.helpers.fail
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker


fun ICorChainDsl<TodoContext>.validateTitleHasContent(title: String) = worker {
    this.title = title
    this.description = """
        Проверяем, что у нас есть какие-то слова в заголовке.
        Отказываем в публикации заголовков, в которых только бессмысленные символы типа %^&^$^%#^))&^*&%^^&
    """.trimIndent()
    val regExp = Regex("\\p{L}")
    on { todoValidating.title.isNotEmpty() && ! todoValidating.title.contains(regExp) }
    handle {
        fail(
            errorValidation(
            field = "title",
            violationCode = "noContent",
            description = "field must contain letters"
        )
        )
    }
}
