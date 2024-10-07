pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "ru.otus"

//includeBuild("training-modules")
includeBuild("todo-projects-libs")
includeBuild("todo-projects-tests")

includeBuild("todo-projects")
includeBuild("pgkn")