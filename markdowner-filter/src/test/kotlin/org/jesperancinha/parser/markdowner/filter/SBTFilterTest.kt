package org.jesperancinha.parser.markdowner.filter

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Path

internal class SBTFilterTest {
    @Test
    fun testSBTFilter() {
        val resource = Path.of(javaClass.getResource("/directory2NoReadme/project5Sbt/build.sbt").path)
        val npmFilter: ProjectFilter<Path> = SBTFilter()
        val result = npmFilter.test(resource)
        Assertions.assertThat(npmFilter.lastProjectName()).isEqualTo("sbt-project")
        Assertions.assertThat(result).isTrue
    }

    @Test
    fun testSBTFilterFail() {
        val resource = Path.of(javaClass.getResource("/directory2NoReadme/project3MavenAndNPM/package.json").path)
        val mavenFilter: ProjectFilter<Path> = SBTFilter()
        val result = mavenFilter.test(resource)
        Assertions.assertThat(result).isFalse
    }
}