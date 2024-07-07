pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion
    }
}

rootProject.name = "marketplace"

includeBuild("build-plugin")
includeBuild("training-modules")
includeBuild("todo-projects")

dependencyResolutionManagement {
    versionCatalogs {
        create("librariesCatalog") {
            from(files("gradle/libraries.versions.toml"))
        }
    }
}