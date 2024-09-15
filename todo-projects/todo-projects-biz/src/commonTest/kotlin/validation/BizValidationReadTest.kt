package ru.otus.todo.biz.validation


import models.TodoCommand
import kotlin.test.Test

class BizValidationReadTest: BaseBizValidationTest() {
    override val command = TodoCommand.READ

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)

}
