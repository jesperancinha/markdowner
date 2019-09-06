package org.jesperancinha.parser.markdowner.filter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

public class GradleFilterTest {

    @Test
    public void testGradleFilter() {
        final Path resource = Path.of(getClass().getResource("/directory2NoReadme/project4Gradle/gradle.build").getPath());
        final ProjectFilter<Path> npmFilter = new GradleFilter();

        boolean result = npmFilter.test(resource);

        assertThat(result).isTrue();
        assertThat(npmFilter.lastProjectName()).isEqualTo("project4Gradle");
    }

    @Test
    public void testGradleFilterFail() {
        final Path resource = Path.of(getClass().getResource("/directory2NoReadme/project3MavenAndNPM/package.json").getPath());
        final ProjectFilter<Path> mavenFilter = new GradleFilter();

        boolean result = mavenFilter.test(resource);

        assertThat(result).isFalse();
    }
}