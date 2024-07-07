rootProject.name = "backend-build"

dependencyResolutionManagement {
    versionCatalogs {
        create("librariesCatalog") {
            from(files("../gradle/libraries.versions.toml"))
        }
    }
}
