package ru.otus.todo.biz.repo

import ru.otus.todo.biz.exceptions.TodoDbNotConfiguredException
import ru.otus.todo.common.TodoContext
import ru.otus.todo.common.helpers.errorSystem
import ru.otus.todo.common.helpers.fail
import ru.otus.todo.common.models.TodoWorkMode
import ru.otus.todo.common.repo.IRepoTodo
import ru.otus.todo.cor.ICorChainDsl
import ru.otus.todo.cor.worker

fun ICorChainDsl<TodoContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от зпрошенного режима работы        
    """.trimIndent()
    handle {
        todoRepo = when {
            workMode == TodoWorkMode.TEST -> corSettings.repoTest
            workMode == TodoWorkMode.STUB -> corSettings.repoStub
            else -> corSettings.repoProd
        }
        if (workMode != TodoWorkMode.STUB && todoRepo == IRepoTodo.NONE) fail(
            errorSystem(
                violationCode = "dbNotConfigured",
                e = TodoDbNotConfiguredException(workMode)
            )
        )
    }
}
