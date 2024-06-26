pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion
    }
}

rootProject.name = "marketplace"

dependencyResolutionManagement {
    versionCatalogs {
        create("libraries") {
            from(files("gradle/libraries.versions.toml"))
        }
    }
}


include("plugin")
include("training-modules")
include("project-modules")

