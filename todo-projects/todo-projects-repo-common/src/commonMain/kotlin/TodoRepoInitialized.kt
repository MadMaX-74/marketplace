package ru.otus.otuskotlin.marketplace.repo.common

import models.Todo


/**
 * Делегат для всех репозиториев, позволяющий инициализировать базу данных предзагруженными данными
 */
class TodoRepoInitialized(
    private val repo: IRepoTodoInitializable,
    initObjects: Collection<Todo> = emptyList(),
) : IRepoTodoInitializable by repo {
    @Suppress("unused")
    val initializedObjects: List<Todo> = save(initObjects).toList()
}
