package ru.otus.todo.repo.postgresql


import ru.otus.todo.common.repo.IRepoTodo
import ru.otus.todo.repo.common.IRepoTodoInitializable
import ru.otus.todo.repo.common.TodoRepoInitialized
import ru.otus.todo.repo.tests.RepoTodoCreateTest
import ru.otus.todo.repo.tests.RepoTodoDeleteTest
import ru.otus.todo.repo.tests.RepoTodoListTest
import ru.otus.todo.repo.tests.RepoTodoReadTest
import ru.otus.todo.repo.tests.RepoTodoUpdateTest
import kotlin.test.AfterTest

private fun IRepoTodo.clear() {
    val pgRepo = (this as TodoRepoInitialized).repo as RepoTodoSql
    pgRepo.clear()
}

class RepoTodoSQLCreateTest : RepoTodoCreateTest() {
    override val repo: IRepoTodoInitializable = SqlTestCompanion.repoUnderTestContainer(
        initObjects,
        randomUuid = { uuidNew.asString() },
    )
    @AfterTest
    fun tearDown() = repo.clear()
}

class RepoTodoSQLReadTest : RepoTodoReadTest() {
    override val repo: IRepoTodo = SqlTestCompanion.repoUnderTestContainer(initObjects)
    @AfterTest
    fun tearDown() = repo.clear()
}

class RepoTodoSQLUpdateTest : RepoTodoUpdateTest() {
    override val repo: IRepoTodo = SqlTestCompanion.repoUnderTestContainer(
        initObjects
    )
    @AfterTest
    fun tearDown() {
        repo.clear()
    }
}

class RepoTodoSQLDeleteTest : RepoTodoDeleteTest() {
    override val repo: IRepoTodo = SqlTestCompanion.repoUnderTestContainer(initObjects)
    @AfterTest
    fun tearDown() = repo.clear()
}

class RepoTodoSQLListTest : RepoTodoListTest() {
    override val repo: IRepoTodo = SqlTestCompanion.repoUnderTestContainer(initObjects)
    @AfterTest
    fun tearDown() = repo.clear()
}
