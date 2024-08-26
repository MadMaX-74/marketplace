plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependencies)
    alias(libs.plugins.spring.kotlin)
    alias(libs.plugins.kotlinx.serialization)
    id("build-jvm")
}

dependencies {
    implementation(libs.spring.actuator)
    implementation(libs.spring.webflux)
    implementation(libs.spring.webflux.ui)
    implementation(libs.jackson.kotlin)
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.reactor)
    implementation(libs.coroutines.reactive)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    // RabbitMQ
    implementation("org.springframework.boot:spring-boot-starter-amqp")

    // Внутренние модели
    implementation(project(":todo-projects-common"))
    implementation(project(":todo-projects-app-common"))

    // v1 api
    implementation(project(":todo-projects-api-v1"))
    implementation(project(":todo-projects-mapper"))

    // biz
    implementation(project(":todo-projects-biz"))

    // tests
    testImplementation(kotlin("test-junit5"))
    testImplementation(libs.spring.test)
    testImplementation(libs.testcontainers.core)
    testImplementation(libs.testcontainers.rabbitmq)
    testImplementation(libs.mockito.kotlin)

    // stubs
    testImplementation(project(":todo-projects-stubs"))
}

tasks {
    withType<ProcessResources> {
        val files = listOf("spec-v1").map {
            rootProject.ext[it]
        }
        from(files) {
            into("/static")
            filter {
                // Устанавливаем версию в сваггере
                it.replace("\${VERSION_APP}", project.version.toString())
            }

        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
