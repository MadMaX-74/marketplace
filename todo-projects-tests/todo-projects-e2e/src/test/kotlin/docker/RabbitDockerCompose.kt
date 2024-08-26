package ru.otus.todo.tests.e2e.docker

import ru.otus.todo.tests.e2e.fixture.docker.AbstractDockerCompose

object RabbitDockerCompose : AbstractDockerCompose(
    "rabbit_1", 5672, "docker-compose-rabbit.yml"
) {
    override val user: String
        get() = "guest"
    override val password: String
        get() = "guest"
}
