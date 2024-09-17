package ru.otus.todo.biz.validation


import models.TodoCommand
import kotlin.test.Test

class BizValidationDeleteTest: BaseBizValidationTest() {
    override val command = TodoCommand.DELETE

    @Test fun correctId() = validationIdCorrect(command, processor)

}