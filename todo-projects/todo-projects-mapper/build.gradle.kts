plugins {
    id("build-jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":todo-projects-api-v1"))
    implementation(project(":todo-projects-common"))

    testImplementation(kotlin("test-junit"))
}
