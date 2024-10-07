import ru.otus.todo.repo.common.TodoRepoInitialized
import ru.otus.todo.repo.inmemory.TodoRepoInMemory
import ru.otus.todo.repo.tests.RepoTodoCreateTest
import ru.otus.todo.repo.tests.RepoTodoDeleteTest
import ru.otus.todo.repo.tests.RepoTodoListTest
import ru.otus.todo.repo.tests.RepoTodoReadTest
import ru.otus.todo.repo.tests.RepoTodoUpdateTest


class TodoRepoInMemoryCreateTest : RepoTodoCreateTest() {
    override val repo = TodoRepoInitialized(
        TodoRepoInMemory(randomUuid = { uuidNew.asString() }),
        initObjects = initObjects,
    )
}

class TodoRepoInMemoryDeleteTest : RepoTodoDeleteTest() {
    override val repo = TodoRepoInitialized(
        TodoRepoInMemory(),
        initObjects = initObjects,
    )
}

class TodoRepoInMemoryReadTest : RepoTodoReadTest() {
    override val repo = TodoRepoInitialized(
        TodoRepoInMemory(),
        initObjects = initObjects,
    )
}

class TodoRepoInMemoryListTest : RepoTodoListTest() {
    override val repo = TodoRepoInitialized(
        TodoRepoInMemory(),
        initObjects = initObjects,
    )
}

class TodoRepoInMemoryUpdateTest : RepoTodoUpdateTest() {
    override val repo = TodoRepoInitialized(
        TodoRepoInMemory(),
        initObjects = initObjects,
    )
}
