package org.jesperancinha.parser.markdowner.filter;

import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class MavenFilterTest {

    @Test
    public void testMavenFilter() {
        final var resource1 = getClass().getResource("/directory2NoReadme/project1Maven/pom.xml");
        assertThat(resource1).isNotNull();
        final var resource = Path.of(resource1.getPath());
        final var mavenFilter = new MavenFilter();

        boolean result = mavenFilter.test(resource);
        assertThat(result).isTrue();
        assertThat(mavenFilter.lastProjectName()).isEqualTo("This is a test project");
    }

    @Test
    public void testMavenFilterFail() {
        final var resource1 = getClass().getResource("/directory2NoReadme/project3MavenAndNPM/package.json");
        assertThat(resource1).isNotNull();
        final var resource = Path.of(resource1.getPath());
        final var mavenFilter = new MavenFilter();

        final var result = mavenFilter.test(resource);

        assertThat(result).isFalse();
    }
}