package org.jesperancinha.parser.markdowner.filter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;


public class NPMFilterTest {

    @Test
    public void testNPMFilter() {
        final Path resource = Path.of(getClass().getResource("/directory2NoReadme/project3MavenAndNPM/package.json").getPath());
        final ProjectFilter<Path> npmFilter = new NPMFilter();

        boolean result = npmFilter.test(resource);

        assertThat(result).isTrue();
        assertThat(npmFilter.lastProjectName()).isEqualTo("npm-project");
    }

    @Test
    public void testNPMFilterFail() {
        final Path resource = Path.of(getClass().getResource("/directory2NoReadme/project1Maven/pom.xml").getPath());
        final ProjectFilter<Path> mavenFilter = new NPMFilter();

        boolean result = mavenFilter.test(resource);

        assertThat(result).isFalse();
    }
}