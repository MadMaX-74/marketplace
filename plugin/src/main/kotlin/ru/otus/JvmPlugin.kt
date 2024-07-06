package ru.otus

import org.gradle.api.Plugin
import org.gradle.api.Project

class JvmPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("buildJVM") {
            it.doLast {
                println("Building JVM Project")
            }
        }
    }
}