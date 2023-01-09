package org.jesperancinha.parser.markdowner.filter

import lombok.extern.slf4j.Slf4j
import java.nio.file.Path

/**
 * Filter to check if folder contains a Gradle project and keeps the project name in memory
 */
@Slf4j
class GradleFilter : ProjectFilter<Path?>() {
    override fun test(path: Path?): Boolean {
        val isGradleBuild = gradleBuilds.contains(path.getFileName().toString())
        if (isGradleBuild) {
            lastProjectName = path.getParent().fileName.toString()
            return true
        }
        return false
    }

    override fun lastProjectName(): String? {
        return lastProjectName
    }

    companion object {
        private val gradleBuilds: MutableList<String?>? = listOf<String?>(
            "gradle.build", "build.gradle"
        )
    }
}