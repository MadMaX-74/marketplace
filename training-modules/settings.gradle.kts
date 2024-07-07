pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "training-modules"

dependencyResolutionManagement {
    versionCatalogs {
        create("librariesCatalog") {
            from(files("../gradle/libraries.versions.toml"))
        }
    }
}

include("test-lesson")


