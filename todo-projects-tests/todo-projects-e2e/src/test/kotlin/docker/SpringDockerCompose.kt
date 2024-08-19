package ru.otus.todo.tests.e2e.docker

import ru.otus.todo.tests.e2e.fixture.docker.AbstractDockerCompose

object SpringDockerCompose : AbstractDockerCompose(
    "app-spring_1", 8080, "docker-compose-spring.yml"
)
