package org.jesperancinha.parser.markdowner.filter

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.nio.file.Path

class GradleFilterTest {
    @Test
    fun testGradleFilter() {
        val gradleBuild = javaClass.getResource("/directory2NoReadme/project4Gradle/gradle.build")
        gradleBuild.shouldNotBeNull()
        val resource = Path.of(gradleBuild.path)
        val npmFilter = GradleFilter()
        val result = npmFilter.test(resource)
        result.shouldBeTrue()
        npmFilter.lastProjectName() shouldBe "project4Gradle"
    }

    @Test
    fun testGradleFilterFail() {
        val packageJson = javaClass.getResource("/directory2NoReadme/project3MavenAndNPM/package.json")
        packageJson.shouldNotBeNull()
        val resource = Path.of(packageJson.path)
        val mavenFilter = GradleFilter()
        val result = mavenFilter.test(resource)
        result.shouldBeFalse()
    }
}