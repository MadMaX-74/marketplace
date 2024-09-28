package repo

interface IRepoTodo {
    suspend fun createTodo(rq: DbTodoRequest): IDbTodoResponse
    suspend fun readTodo(rq: DbTodoIdRequest): IDbTodoResponse
    suspend fun updateTodo(rq: DbTodoRequest): IDbTodoResponse
    suspend fun deleteTodo(rq: DbTodoIdRequest): IDbTodoResponse
    suspend fun listTodo(rq: DbTodoListRequest): IDbTodosResponse
    companion object {
        val NONE = object : IRepoTodo {
            override suspend fun createTodo(rq: DbTodoRequest): IDbTodoResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun readTodo(rq: DbTodoIdRequest): IDbTodoResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun updateTodo(rq: DbTodoRequest): IDbTodoResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun deleteTodo(rq: DbTodoIdRequest): IDbTodoResponse {
                throw NotImplementedError("Must not be used")
            }

            override suspend fun listTodo(rq: DbTodoListRequest): IDbTodosResponse {
                throw NotImplementedError("Must not be used")
            }
        }
    }
}
