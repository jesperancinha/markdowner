package org.jesperancinha.parser.markdowner.filter;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class SBTFilterTest {

    @Test
    public void testSBTFilter() {
        final Path resource = Path.of(getClass().getResource("/directory2NoReadme/project5Sbt/build.sbt").getPath());
        final ProjectFilter<Path> npmFilter = new SBTFilter();

        boolean result = npmFilter.test(resource);

        assertThat(npmFilter.lastProjectName()).isEqualTo("sbt-project");
        assertThat(result).isTrue();
    }

    @Test
    public void testSBTFilterFail() {
        final Path resource = Path.of(getClass().getResource("/directory2NoReadme/project3MavenAndNPM/package.json").getPath());
        final ProjectFilter<Path> mavenFilter = new SBTFilter();

        boolean result = mavenFilter.test(resource);

        assertThat(result).isFalse();
    }
}