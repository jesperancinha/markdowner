package org.jesperancinha.projectsigner.filter;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;


public class NPMFilterTest {

    @Test
    void testNPMFilter() {
        final Path resource = Path.of(getClass().getResource("/directory2NoReadme/project3MavenAndNPM/package.json").getPath());
        final ProjectFilter<Path> npmFilter = new NPMFilter();

        boolean result = npmFilter.test(resource);

        assertThat(result).isTrue();
        assertThat(npmFilter.lastProjectName()).isEqualTo("npm-project");
    }

    @Test
    void testNPMFilterFail() {
        final Path resource = Path.of(getClass().getResource("/directory2NoReadme/project1Maven/pom.xml").getPath());
        final ProjectFilter<Path> mavenFilter = new NPMFilter();

        boolean result = mavenFilter.test(resource);

        assertThat(result).isFalse();
    }
}