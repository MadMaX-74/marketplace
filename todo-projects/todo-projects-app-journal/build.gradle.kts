plugins {
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinx.serialization)
    id("build-jvm") // Плагин для JVM
}

dependencies {
    implementation(libs.ktor.server.core) // Основные зависимости для Ktor
    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.yaml)
    implementation(libs.ktor.server.negotiation)


    implementation("com.rabbitmq:amqp-client:5.17.1") // Зависимость для RabbitMQ

    // Apache Commons CSV
    implementation("org.apache.commons:commons-csv:1.10.0")

    // Kotlinx Serialization
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.serialization.json)

    // Внутренние модели
    implementation(project(":todo-projects-common"))
    implementation(project(":todo-projects-app-common"))

    // v1 api
    implementation(project(":todo-projects-api-v1"))
    implementation(project(":todo-projects-mapper"))

    // biz
    implementation(project(":todo-projects-biz"))

    // тесты
    testImplementation(kotlin("test-junit5"))
    testImplementation(libs.ktor.server.test)
    testImplementation(libs.mockito.kotlin)
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

    withType<Test> {
        useJUnitPlatform()
    }
}
kotlin {
    sourceSets {
        all {
            languageSettings {
                optIn("kotlin.ExperimentalStdlibApi")
            }
        }
    }
}
