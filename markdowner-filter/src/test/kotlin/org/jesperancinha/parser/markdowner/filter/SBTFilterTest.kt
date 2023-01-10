package org.jesperancinha.parser.markdowner.filter

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.nio.file.Path

internal class SBTFilterTest {
    @Test
    fun testSBTFilter() {
        val buildSbt = javaClass.getResource("/directory2NoReadme/project5Sbt/build.sbt")
        buildSbt.shouldNotBeNull()
        val resource = Path.of(buildSbt.path)
        val npmFilter: ProjectFilter<Path> = SBTFilter()
        val result = npmFilter.test(resource)
        result.shouldBeTrue()
        npmFilter.shouldNotBeNull()
        npmFilter.lastProjectName() shouldBe "sbt-project"
    }

    @Test
    fun testSBTFilterFail() {
        val packageJson = javaClass.getResource("/directory2NoReadme/project3MavenAndNPM/package.json")
        packageJson.shouldNotBeNull()
        val resource = Path.of(packageJson.path)
        val mavenFilter: ProjectFilter<Path> = SBTFilter()
        val result = mavenFilter.test(resource)
        result.shouldBeFalse()
    }
}