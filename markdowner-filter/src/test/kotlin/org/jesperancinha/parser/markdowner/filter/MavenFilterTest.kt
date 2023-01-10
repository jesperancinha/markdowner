package org.jesperancinha.parser.markdowner.filter

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.nio.file.Path

class MavenFilterTest {
    @Test
    fun testMavenFilter() {
        val pomXml = javaClass.getResource("/directory2NoReadme/project1Maven/pom.xml")
        pomXml.shouldNotBeNull()
        val resource = Path.of(pomXml.path)
        val mavenFilter = MavenFilter()
        val result = mavenFilter.test(resource)
        result.shouldBeTrue()
        mavenFilter.lastProjectName() shouldBe "This is a test project"
    }

    @Test
    fun testMavenFilterFail() {
        val packageJson = javaClass.getResource("/directory2NoReadme/project3MavenAndNPM/package.json")
        packageJson.shouldNotBeNull()
        val resource = Path.of(packageJson.path)
        val mavenFilter = MavenFilter()
        val result = mavenFilter.test(resource)
        result.shouldBeFalse()
    }
}