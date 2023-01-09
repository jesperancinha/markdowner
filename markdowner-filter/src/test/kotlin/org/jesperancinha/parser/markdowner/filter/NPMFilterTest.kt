package org.jesperancinha.parser.markdowner.filter

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Path

class NPMFilterTest {
    @Test
    fun testNPMFilter() {
        val resource = Path.of(javaClass.getResource("/directory2NoReadme/project3MavenAndNPM/package.json").path)
        val npmFilter: ProjectFilter<Path?> = NPMFilter()
        val result = npmFilter.test(resource)
        Assertions.assertThat(result).isTrue
        Assertions.assertThat(npmFilter.lastProjectName()).isEqualTo("npm-project")
    }

    @Test
    fun testNPMFilterFail() {
        val resource = Path.of(javaClass.getResource("/directory2NoReadme/project1Maven/pom.xml").path)
        val mavenFilter: ProjectFilter<Path?> = NPMFilter()
        val result = mavenFilter.test(resource)
        Assertions.assertThat(result).isFalse
    }
}