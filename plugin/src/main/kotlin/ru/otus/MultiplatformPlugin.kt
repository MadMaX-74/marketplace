package ru.otus

import org.gradle.api.Plugin
import org.gradle.api.Project

class MultiplatformPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("buildMultiplatform") {
            it.doLast {
                println("Building Multiplatform Project")
            }
        }
    }
}