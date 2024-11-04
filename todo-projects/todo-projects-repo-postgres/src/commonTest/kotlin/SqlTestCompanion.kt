package ru.otus.todo.repo.postgresql

import com.benasher44.uuid.uuid4
import ru.otus.todo.common.models.Todo
import ru.otus.todo.repo.common.IRepoTodoInitializable
import ru.otus.todo.repo.common.TodoRepoInitialized

object SqlTestCompanion {
    private const val HOST = "localhost"
    private const val USER = "postgres"
    private const val PASS = "marketplace-pass"
    val PORT = getEnv("postgresPort")?.toIntOrNull() ?: 5433

    fun repoUnderTestContainer(
        initObjects: Collection<Todo> = emptyList(),
        randomUuid: () -> String = { uuid4().toString() },
    ): IRepoTodoInitializable = TodoRepoInitialized(
        repo = RepoTodoSql(
            SqlProperties(
                host = HOST,
                user = USER,
                password = PASS,
                port = PORT,
            ),
            randomUuid = randomUuid
        ),
        initObjects = initObjects,
    )
}

