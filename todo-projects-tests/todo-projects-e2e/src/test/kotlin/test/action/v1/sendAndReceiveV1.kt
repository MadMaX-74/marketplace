package ru.otus.todo.tests.e2e.test.action.v1

import co.touchlab.kermit.Logger
import ru.otus.todo.api.v1.models.IRequest
import ru.otus.todo.api.v1.models.IResponse
import ru.otus.todo.tests.e2e.fixture.client.Client
import ru.otus.todoprojects.api.v1.apiV1RequestSerialize
import ru.otus.todoprojects.api.v1.apiV1ResponseDeserialize

private val log = Logger

suspend fun Client.sendAndReceive(path: String, request: IRequest): IResponse {
    val requestBody = apiV1RequestSerialize(request)
    log.i { "Send to v1/$path\n$requestBody" }

    val responseBody = sendAndReceive("v1", path, requestBody)
    log.i { "Received\n$responseBody" }

    return apiV1ResponseDeserialize(responseBody)
}
