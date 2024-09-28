package ru.otus.todo.biz.validation


import ru.otus.todo.common.models.TodoCommand
import kotlin.test.Test

class BizValidationReadTest: BaseBizValidationTest() {
    override val command = TodoCommand.READ

    @Test fun correctId() = validationIdCorrect(command, processor)

}
