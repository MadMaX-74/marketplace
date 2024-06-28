plugins {
    kotlin("jvm")
    `java-gradle-plugin`
}

group = "com.otus.otuskotlin.marketplace"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(librariesCatalog.kotlin.stdlib)
    testImplementation(librariesCatalog.junit.jupiter)
}
kotlin {
    jvmToolchain(21)
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
sourceSets {
    main {
        kotlin {
            srcDir("src/main/kotlin")
        }
    }
}