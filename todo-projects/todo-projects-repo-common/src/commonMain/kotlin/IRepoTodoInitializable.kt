package ru.otus.todo.repo.common

import ru.otus.todo.common.models.Todo
import ru.otus.todo.common.repo.IRepoTodo

interface IRepoTodoInitializable: IRepoTodo {
    fun save(todos: Collection<Todo>): Collection<Todo>
}
