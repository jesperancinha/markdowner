package org.jesperancinha.projectsigner.filter;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SBTFilterTest {

    @Test
    void testSBTFilter() {
        final Path resource = Path.of(getClass().getResource("/directory2NoReadme/project5Sbt/build.sbt").getPath());
        final ProjectFilter npmFilter = new SBTFilter();

        boolean result = npmFilter.test(resource);

        assertThat(result).isTrue();
    }

    @Test
    void testSBTFilterFail() {
        final Path resource = Path.of(getClass().getResource("/directory2NoReadme/project3MavenAndNPM/package.json").getPath());
        final ProjectFilter mavenFilter = new SBTFilter();

        boolean result = mavenFilter.test(resource);

        assertThat(result).isFalse();
    }
}