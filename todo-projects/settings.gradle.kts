rootProject.name = "todo-projects"

dependencyResolutionManagement {
    versionCatalogs {
        create("librariesCatalog") {
            from(files("../gradle/libraries.versions.toml"))
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

include("todo-service")
include("todo-projects-api-v1")


