package ru.otus.otuskotlin.marketplace.repo.common

import models.Todo
import repo.IRepoTodo


interface IRepoTodoInitializable: IRepoTodo {
    fun save(ads: Collection<Todo>): Collection<Todo>
}
