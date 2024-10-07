package ru.otus.todo.common

import ru.otus.todo.common.repo.IRepoTodo

data class TodoCorSettings(
    val loggerProvider: Any? = null,
    val repoStub: IRepoTodo = IRepoTodo.NONE,
    val repoTest: IRepoTodo = IRepoTodo.NONE,
    val repoProd: IRepoTodo = IRepoTodo.NONE,
) {
    companion object {
        val NONE = TodoCorSettings()
    }
}
