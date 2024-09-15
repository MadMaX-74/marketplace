package ru.otus.todo.biz.validation



import models.TodoCommand
import kotlin.test.Test

class BizValidationUpdateTest: BaseBizValidationTest() {
    override val command = TodoCommand.UPDATE

    @Test fun correctTitle() = validationTitleCorrect(command, processor)
    @Test fun emptyTitle() = validationTitleEmpty(command, processor)
    @Test fun badSymbolsTitle() = validationTitleSymbols(command, processor)


    @Test fun correctId() = validationIdCorrect(command, processor)


}
