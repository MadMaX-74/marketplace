plugins {
    kotlin("jvm")
}

group = "ru.otus.todo-projects"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {
    repositories {
        mavenCentral()
    }
    group = rootProject.group
    version = rootProject.version
}
