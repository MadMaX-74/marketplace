package ru.otus.todo.biz.exceptions

import ru.otus.todo.common.models.TodoWorkMode


class TodoDbNotConfiguredException(val workMode: TodoWorkMode): Exception(
    "Database is not configured properly for workmode $workMode"
)
