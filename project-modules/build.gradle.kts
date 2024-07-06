plugins {
    kotlin("jvm")
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

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}