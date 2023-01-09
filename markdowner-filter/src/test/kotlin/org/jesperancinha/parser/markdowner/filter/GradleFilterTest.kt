package org.jesperancinha.parser.markdowner.filter

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Path

class GradleFilterTest {
    @Test
    fun testGradleFilter() {
        val resource1 = javaClass.getResource("/directory2NoReadme/project4Gradle/gradle.build")
        Assertions.assertThat(resource1).isNotNull
        val resource = Path.of(resource1.path)
        val npmFilter = GradleFilter()
        val result = npmFilter.test(resource)
        Assertions.assertThat(result).isTrue
        Assertions.assertThat(npmFilter.lastProjectName()).isEqualTo("project4Gradle")
    }

    @Test
    fun testGradleFilterFail() {
        val resource1 = javaClass.getResource("/directory2NoReadme/project3MavenAndNPM/package.json")
        Assertions.assertThat(resource1).isNotNull
        val resource = Path.of(resource1.path)
        val mavenFilter = GradleFilter()
        val result = mavenFilter.test(resource)
        Assertions.assertThat(result).isFalse
    }
}