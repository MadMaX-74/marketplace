plugins {
    kotlin("jvm")
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