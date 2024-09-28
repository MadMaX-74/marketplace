package ru.otus.todo.biz.validation


import ru.otus.todo.common.models.TodoCommand
import kotlin.test.Test

class BizValidationDeleteTest: BaseBizValidationTest() {
    override val command = TodoCommand.DELETE

    @Test fun correctId() = validationIdCorrect(command, processor)

}