package ru.otus.todo.biz.validation

import models.TodoCommand
import kotlin.test.Test

class BizValidationCreateTest: BaseBizValidationTest() {
    override val command: TodoCommand = TodoCommand.CREATE

    @Test fun correctTitle() = validationTitleCorrect(command, processor)
    @Test fun emptyTitle() = validationTitleEmpty(command, processor)
    @Test fun badSymbolsTitle() = validationTitleSymbols(command, processor)
}
