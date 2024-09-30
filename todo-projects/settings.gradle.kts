rootProject.name = "todo-projects"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}


pluginManagement {
    includeBuild("../build-plugin")
    plugins {
        id("build-jvm") apply false
        id("build-kmp") apply false
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

include(":todo-projects-api-v1")
include(":todo-projects-mapper")
include(":todo-projects-common")

include(":todo-projects-stubs")
include(":todo-projects-biz")

include(":todo-projects-app-common")
include(":todo-projects-app-todo")
include(":todo-projects-app-journal")

include(":todo-projects-repo-common")
include(":todo-projects-repo-stubs")
include(":todo-projects-repo-tests")
include(":todo-projects-repo-inmemory")
include(":todo-projects-repo-postgres")


