plugins {
    kotlin("jvm")
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("ru.otus.todo:todo-projects-api-v1")

    testImplementation(libs.logback)
    testImplementation(libs.kermit)

    testImplementation(libs.bundles.kotest)

//    implementation("com.rabbitmq:amqp-client:$rabbitVersion")

    testImplementation(libs.testcontainers.core)
    testImplementation(libs.coroutines.core)

    testImplementation(libs.ktor.client.core)
    testImplementation(libs.ktor.client.okhttp)
//    testImplementation("io.ktor:ktor-client-core:$ktorVersion")
//    testImplementation("io.ktor:ktor-client-okhttp:$ktorVersion")
//    testImplementation("io.ktor:ktor-client-okhttp-jvm:$ktorVersion")
}

var severity: String = "MINOR"

tasks {
    withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
