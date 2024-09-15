package ru.otus.todo.biz

import TodoContext
import TodoCorSettings
import models.TodoCommand
import models.TodoId
import ru.otus.todo.biz.general.initStatus
import ru.otus.todo.biz.general.operation
import ru.otus.todo.biz.general.stubs
import ru.otus.todo.biz.stubs.*
import ru.otus.todo.biz.validation.*
import ru.otus.todo.cor.rootChain
import ru.otus.todo.cor.worker


class TodoProcessor(private val corSettings: TodoCorSettings = TodoCorSettings.NONE) {
    suspend fun exec(ctx: TodoContext) = businessChain.exec(ctx.also { it.corSettings = corSettings })

    private val businessChain = rootChain<TodoContext> {
        initStatus("Инициализация статуса")

        operation("Создание объявления", TodoCommand.CREATE) {
            stubs("Обработка стабов") {
                stubCreateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadTitle("Имитация ошибки валидации заголовка")
                stubValidationBadDescription("Имитация ошибки валидации описания")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в todoValidating") { todoValidating = todoRequest.deepCopy() }
                worker("Очистка id") { todoValidating.id = TodoId.NONE.toString() }
                worker("Очистка заголовка") { todoValidating.title = todoValidating.title.trim() }
                worker("Очистка описания") { todoValidating.description = todoValidating.description.trim() }
                validateTitleNotEmpty("Проверка, что заголовок не пуст")
                validateTitleHasContent("Проверка символов")
                validateCreatedDateNotEmpty("Проверка даты")
                finishTodoValidation("Завершение проверок")
            }
        }
        operation("Получить объявление", TodoCommand.READ) {
            stubs("Обработка стабов") {
                stubReadSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в todoValidating") { todoValidating = todoRequest.deepCopy() }
                worker("Очистка id") { todoValidating.id = TodoId(todoValidating.id.trim()).toString() }
                validateIdNotEmpty("Проверка на непустой id")
                validateTitleNotEmpty("Проверка, что заголовок не пуст")
                finishTodoValidation("Успешное завершение процедуры валидации")
            }
        }
        operation("Изменить объявление", TodoCommand.UPDATE) {
            stubs("Обработка стабов") {
                stubUpdateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubValidationBadTitle("Имитация ошибки валидации заголовка")
                stubValidationBadDescription("Имитация ошибки валидации описания")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в todoValidating") { todoValidating = todoRequest.deepCopy() }
                worker("Очистка id") { todoValidating.id = TodoId(todoValidating.id.trim()).toString() }
                worker("Очистка заголовка") { todoValidating.title = todoValidating.title.trim() }
                worker("Очистка описания") { todoValidating.description = todoValidating.description.trim() }
                worker("Очистка даты") { todoValidating.createdDate = todoValidating.createdDate }
                validateIdNotEmpty("Проверка на непустой id")
                validateTitleNotEmpty("Проверка на непустой заголовок")
                validateTitleHasContent("Проверка на наличие содержания в заголовке")
                validateCreatedDateNotEmpty("Проверка даты")

                finishTodoValidation("Успешное завершение процедуры валидации")
            }
        }
    }.build()
}
