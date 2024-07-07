plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("jvmPlugin") {
            id = "ru.otus.jvm-plugin"
            implementationClass = "ru.otus.JvmPlugin"
        }
        create("multiplatformPlugin") {
            id = "ru.otus.multiplatform-plugin"
            implementationClass = "ru.otus.MultiplatformPlugin"
        }
    }
}

dependencies {
    implementation(files(librariesCatalog.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(librariesCatalog.plugin.kotlin)
    implementation(librariesCatalog.plugin.binaryCompatibilityValidator)
}