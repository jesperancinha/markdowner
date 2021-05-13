package org.jesperancinha.parser.markdowner.filter;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class GradleFilterTest {

    @Test
    public void testGradleFilter() {
        final var resource1 = getClass().getResource("/directory2NoReadme/project4Gradle/gradle.build");
        assertThat(resource1).isNotNull();
        final var resource = Path.of(resource1.getPath());
        final var npmFilter = new GradleFilter();

        final var result = npmFilter.test(resource);

        assertThat(result).isTrue();
        assertThat(npmFilter.lastProjectName()).isEqualTo("project4Gradle");
    }

    @Test
    public void testGradleFilterFail() {
        final var resource1 = getClass().getResource("/directory2NoReadme/project3MavenAndNPM/package.json");
        assertThat(resource1).isNotNull();
        final var resource = Path.of(resource1.getPath());
        final var mavenFilter = new GradleFilter();

        final var result = mavenFilter.test(resource);

        assertThat(result).isFalse();
    }
}