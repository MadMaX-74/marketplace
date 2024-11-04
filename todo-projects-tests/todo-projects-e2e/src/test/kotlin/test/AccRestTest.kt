package ru.otus.todo.tests.e2e.test

import io.kotest.core.annotation.Ignored
import ru.otus.todo.tests.e2e.docker.SpringDockerCompose
import ru.otus.todo.tests.e2e.docker.WiremockDockerCompose
import ru.otus.todo.tests.e2e.fixture.BaseFunSpec
import ru.otus.todo.tests.e2e.fixture.client.RestClient
import ru.otus.todo.tests.e2e.fixture.docker.DockerCompose
import ru.otus.todo.tests.e2e.test.action.v1.toV1

enum class TestDebug {
    STUB, PROD, TEST
}

// Kotest не сможет подставить правильный аргумент конструктора, поэтому
// нужно запретить ему запускать этот класс
@Ignored
open class AccRestTestBaseFull(dockerCompose: DockerCompose, debug: TestDebug = TestDebug.STUB) : BaseFunSpec(dockerCompose, {
    val restClient = RestClient(dockerCompose)
    testApiV1(restClient, prefix = "rest ", debug = debug.toV1())
})

class AccRestWiremockTest : AccRestTestBaseFull(WiremockDockerCompose)

class AccRestSpringTest : AccRestTestBaseFull(SpringDockerCompose, debug = TestDebug.PROD)
