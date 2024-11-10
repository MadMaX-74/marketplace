package ru.otus.todo.biz

import ru.otus.todo.biz.general.initStatus
import ru.otus.todo.biz.general.operation
import ru.otus.todo.biz.general.stubs
import ru.otus.todo.biz.repo.*
import ru.otus.todo.biz.stubs.*
import ru.otus.todo.biz.validation.*
import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.TodoCorSettings
import ru.otus.todo.common.models.TodoCommand
import ru.otus.todo.common.models.TodoId
import ru.otus.todo.common.models.TodoState
import ru.otus.todo.cor.chain
import ru.otus.todo.cor.rootChain
import ru.otus.todo.cor.worker


class TodoProcessor(private val corSettings: TodoCorSettings = TodoCorSettings.NONE) {
    suspend fun exec(ctx: TodoContext) = businessChain.exec(ctx.also { it.corSettings = corSettings })

    private val businessChain = rootChain<TodoContext> {
        initStatus("Инициализация статуса")
        initRepo("Инициализация репозитория")

        operation("Создание задачи", TodoCommand.CREATE) {
            stubs("Обработка стабов") {
                stubCreateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadTitle("Имитация ошибки валидации заголовка")
                stubValidationBadDescription("Имитация ошибки валидации описания")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в todoValidating") { todoValidating = todoRequest.deepCopy() }
                worker("Очистка id") { todoValidating.id = TodoId.NONE }
                worker("Очистка заголовка") { todoValidating.title = todoValidating.title.trim() }
                worker("Очистка описания") { todoValidating.description = todoValidating.description.trim() }
                validateTitleNotEmpty("Проверка, что заголовок не пуст")
                validateTitleHasContent("Проверка символов")
                validateCreatedDateNotEmpty("Проверка даты")
                finishTodoValidation("Завершение проверок")
            }
            chain {
                title = "Логика сохранения"
                repoPrepareCreate("Подготовка объекта для сохранения")
                repoCreate("Создание объявления в БД")
            }
            prepareResult("Подготовка ответа")
        }
        operation("Получить задачи", TodoCommand.READ) {
            stubs("Обработка стабов") {
                stubReadSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в todoValidating") { todoValidating = todoRequest.deepCopy() }
                worker("Очистка id") { todoValidating.id = TodoId(todoValidating.id.asString().trim())}
                validateIdNotEmpty("Проверка на непустой id")
                finishTodoValidation("Успешное завершение процедуры валидации")
            }
            chain {
                title = "Логика чтения"
                repoReadTodo("Чтение объявления из БД")
                worker {
                    title = "Подготовка ответа для Read"
                    on { state == TodoState.RUNNING }
                    handle { todoRepoDone = todoRepoRead }
                }
            }
            prepareResult("Подготовка ответа")
        }
        operation("Изменить задачу", TodoCommand.UPDATE) {
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
                worker("Очистка id") { todoValidating.id = TodoId(todoValidating.id.asString().trim()) }
                worker("Очистка заголовка") { todoValidating.title = todoValidating.title.trim() }
                worker("Очистка описания") { todoValidating.description = todoValidating.description.trim() }
                worker("Очистка даты") { todoValidating.createdDate = todoValidating.createdDate }
                validateIdNotEmpty("Проверка на непустой id")
                validateTitleNotEmpty("Проверка на непустой заголовок")
                validateTitleHasContent("Проверка на наличие содержания в заголовке")
                validateCreatedDateNotEmpty("Проверка даты")

                finishTodoValidation("Успешное завершение процедуры валидации")
            }
            chain {
                title = "Логика сохранения"
                repoReadTodo("Чтение объявления из БД")
                repoPrepareUpdate("Подготовка объекта для обновления")
                repoUpdate("Обновление объявления в БД")
            }
            prepareResult("Подготовка ответа")
        }
        operation("Удалить задачу", TodoCommand.DELETE) {
            stubs("Обработка стабов") {
                stubDeleteSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в todoValidating") { todoValidating = todoRequest.deepCopy() }
                worker("Очистка id") { todoValidating.id = TodoId(todoValidating.id.asString().trim()) }
                worker("Очистка заголовка") { todoValidating.title = todoValidating.title.trim() }
                worker("Очистка описания") { todoValidating.description = todoValidating.description.trim() }
                worker("Очистка даты") { todoValidating.createdDate = todoValidating.createdDate }
                validateIdNotEmpty("Проверка на непустой id")
                finishTodoValidation("Успешное завершение процедуры валидации")
            }
            chain {
                title = "Логика удаления"
                repoReadTodo("Чтение объявления из БД")
                repoPrepareDelete("Подготовка объекта для удаления")
                repoDelete("Удаление объявления из БД")
            }
            prepareResult("Подготовка ответа")
        }
        operation("Возврат списка задач", TodoCommand.LIST) {
            stubs("Обработка стабов") {
                stubListSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            repoList("Чтение списка задач из БД")
            prepareResult("Подготовка ответа")
        }
    }.build()
}
