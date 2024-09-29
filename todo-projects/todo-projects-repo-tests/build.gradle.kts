plugins {
    id("build-kmp")
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(kotlin("test-common"))
                api(kotlin("test-annotations-common"))

                api(libs.coroutines.core)
                api(libs.coroutines.test)
                implementation(project(":todo-projects-common"))
                implementation(project(":todo-projects-repo-common"))
            }
        }
        commonTest {
            dependencies {
                implementation(project(":todo-projects-stubs"))
            }
        }
        jvmMain {
            dependencies {
                api(kotlin("test-junit"))
            }
        }
    }
}
