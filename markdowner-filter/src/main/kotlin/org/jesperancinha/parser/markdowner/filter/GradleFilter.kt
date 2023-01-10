package org.jesperancinha.parser.markdowner.filter

import java.nio.file.Path

/**
 * Filter to check if folder contains a Gradle project and keeps the project name in memory
 */
class GradleFilter : ProjectFilter<Path>() {
    override fun test(path: Path): Boolean {
        val isGradleBuild = gradleBuilds.contains(path.fileName.toString())
        if (isGradleBuild) {
            lastProjectName = path.parent.fileName.toString()
            return true
        }
        return false
    }

    override fun lastProjectName(): String? {
        return lastProjectName
    }

    companion object {
        private val gradleBuilds = listOf(
            "gradle.build", "build.gradle", "build.gradle.kts"
        )
    }
}