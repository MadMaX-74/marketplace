package ru.otus.todo.tests.e2e.test

import io.kotest.core.annotation.Ignored
import ru.otus.todo.tests.e2e.docker.WiremockDockerCompose
import ru.otus.todo.tests.e2e.fixture.BaseFunSpec
import ru.otus.todo.tests.e2e.fixture.client.RestClient
import ru.otus.todo.tests.e2e.fixture.docker.DockerCompose

// Kotest не сможет подставить правильный аргумент конструктора, поэтому
// нужно запретить ему запускать этот класс
@Ignored
open class AccRestTestBase(dockerCompose: DockerCompose) : BaseFunSpec(dockerCompose, {
    val restClient = RestClient(dockerCompose)
    testApiV1(restClient, "rest ")
})

class AccRestWiremockTest : AccRestTestBase(WiremockDockerCompose)
//class AccRestSpringTest : AccRestTestBase(SpringDockerCompose)
//class AccRestKtorTest : AccRestTestBase(KtorDockerCompose)
