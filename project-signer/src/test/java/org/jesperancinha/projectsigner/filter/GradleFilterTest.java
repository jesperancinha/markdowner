package org.jesperancinha.projectsigner.filter;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class GradleFilterTest {

    @Test
    void testGradleFilter() {
        final Path resource = Path.of(getClass().getResource("/directory2NoReadme/project4Gradle/gradle.build").getPath());
        final ProjectFilter npmFilter = new GradleFilter();

        boolean result = npmFilter.test(resource);

        assertThat(result).isTrue();
    }

    @Test
    void testGradleFilterFail() {
        final Path resource = Path.of(getClass().getResource("/directory2NoReadme/project3MavenAndNPM/package.json").getPath());
        final ProjectFilter mavenFilter = new GradleFilter();

        boolean result = mavenFilter.test(resource);

        assertThat(result).isFalse();
    }
}