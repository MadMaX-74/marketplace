package repo

import helpers.errorSystem


abstract class TodoRepoBase: IRepoTodo {

    protected suspend fun tryAdMethod(block: suspend () -> IDbTodoResponse) = try {
        block()
    } catch (e: Throwable) {
        DbTodoResponseErr(errorSystem("methodException", e = e))
    }

    protected suspend fun tryAdsMethod(block: suspend () -> IDbTodosResponse) = try {
        block()
    } catch (e: Throwable) {
        DbTodosResponseErr(errorSystem("methodException", e = e))
    }

}
