package ru.otus.todo.tests.e2e.test.action.v1

import ru.otus.todo.api.v1.models.*
import ru.otus.todo.tests.e2e.test.TestDebug


val debugStubV1 = TaskDebug(mode = TaskDebugMode.TEST, stub = TaskRequestDebugStubs.SUCCESS)

val someCreateTask = TaskCreateObject(
    title = "Task1",
    description = "Task description",
    status = TaskStatus.IN_PROGRESS
)

fun TestDebug.toV1() = when(this) {
    TestDebug.STUB -> debugStubV1
    TestDebug.PROD -> TaskDebug(mode = TaskDebugMode.PROD)
    TestDebug.TEST -> TaskDebug(mode = TaskDebugMode.TEST)
}
