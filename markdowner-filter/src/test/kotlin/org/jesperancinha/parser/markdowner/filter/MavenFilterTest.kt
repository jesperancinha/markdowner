package org.jesperancinha.parser.markdowner.filter

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Path

class MavenFilterTest {
    @Test
    fun testMavenFilter() {
        val resource1 = javaClass.getResource("/directory2NoReadme/project1Maven/pom.xml")
        Assertions.assertThat(resource1).isNotNull
        val resource = Path.of(resource1.path)
        val mavenFilter = MavenFilter()
        val result = mavenFilter.test(resource)
        Assertions.assertThat(result).isTrue
        Assertions.assertThat(mavenFilter.lastProjectName()).isEqualTo("This is a test project")
    }

    @Test
    fun testMavenFilterFail() {
        val resource1 = javaClass.getResource("/directory2NoReadme/project3MavenAndNPM/package.json")
        Assertions.assertThat(resource1).isNotNull
        val resource = Path.of(resource1.path)
        val mavenFilter = MavenFilter()
        val result = mavenFilter.test(resource)
        Assertions.assertThat(result).isFalse
    }
}