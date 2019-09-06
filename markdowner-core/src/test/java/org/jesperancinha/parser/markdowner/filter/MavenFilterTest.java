package org.jesperancinha.parser.markdowner.filter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

public class MavenFilterTest {

    @Test
    public void testMavenFilter() {
        final Path resource = Path.of(getClass().getResource("/directory2NoReadme/project1Maven/pom.xml").getPath());
        final ProjectFilter<Path> mavenFilter = new MavenFilter();

        boolean result = mavenFilter.test(resource);

        assertThat(result).isTrue();
        assertThat(mavenFilter.lastProjectName()).isEqualTo("This is a test project");

    }

    @Test
    public void testMavenFilterFail() {
        final Path resource = Path.of(getClass().getResource("/directory2NoReadme/project3MavenAndNPM/package.json").getPath());
        final ProjectFilter<Path> mavenFilter = new MavenFilter();

        boolean result = mavenFilter.test(resource);

        assertThat(result).isFalse();
    }
}