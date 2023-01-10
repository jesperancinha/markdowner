package org.jesperancinha.parser.markdowner.filter

import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.nio.file.Path

class NPMFilterTest {
    @Test
    fun testNPMFilter() {
        val packageJson = javaClass.getResource("/directory2NoReadme/project3MavenAndNPM/package.json")
        packageJson.shouldNotBeNull()
        val resource = Path.of(packageJson.path)
        val npmFilter: ProjectFilter<Path> = NPMFilter()
        val result = npmFilter.test(resource)
        result.shouldBeTrue()
        npmFilter.lastProjectName() shouldBe "npm-project"
    }

    @Test
    fun testNPMFilterFail() {
        val pomXml = javaClass.getResource("/directory2NoReadme/project1Maven/pom.xml")
        pomXml.shouldNotBeNull()
        val resource = Path.of(pomXml.path)
        val mavenFilter: ProjectFilter<Path> = NPMFilter()
        val result = mavenFilter.test(resource)
        result.shouldBeFalse()
    }
}