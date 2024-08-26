package ru.otus.todo.tests.e2e.docker

import ru.otus.todo.tests.e2e.fixture.docker.AbstractDockerCompose

object KtorDockerCompose : AbstractDockerCompose(
    "app-ktor_1", 8080, "docker-compose-ktor.yml"
)
