package org.jesperancinha.projectsigner.filter;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class MavenFilterTest {

    @Test
    void testMavenFilter() {
        final Path resource = Path.of(getClass().getResource("/directory2NoReadme/project1Maven/pom.xml").getPath());
        final ProjectFilter<Path> mavenFilter = new MavenFilter();

        boolean result = mavenFilter.test(resource);

        assertThat(result).isTrue();
        assertThat(mavenFilter.lastProjectName()).isEqualTo("ProjectMaven");

    }

    @Test
    void testMavenFilterFail() {
        final Path resource = Path.of(getClass().getResource("/directory2NoReadme/project3MavenAndNPM/package.json").getPath());
        final ProjectFilter<Path> mavenFilter = new MavenFilter();

        boolean result = mavenFilter.test(resource);

        assertThat(result).isFalse();
    }
}