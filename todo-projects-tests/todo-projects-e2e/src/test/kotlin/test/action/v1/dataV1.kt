package ru.otus.todo.tests.e2e.test.action.v1

import ru.otus.todo.api.v1.models.*


val debug = TaskDebug(mode = TaskDebugMode.TEST, stub = TaskRequestDebugStubs.SUCCESS)

val someCreateTask = TaskCreateObject(
    title = "Task1",
    description = "Task description",
    status = TaskStatus.IN_PROGRESS
)
