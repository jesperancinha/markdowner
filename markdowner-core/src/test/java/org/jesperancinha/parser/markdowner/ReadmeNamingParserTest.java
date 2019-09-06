package org.jesperancinha.parser.markdowner;

import org.apache.commons.io.IOUtils;
import org.jesperancinha.parser.markdowner.filter.FileFilterChain;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class ReadmeNamingParserTest {

    private Path templatePath = Path.of("/.");

    @Test
    public void testBuildReadmeSamePath() throws IOException {
        final Path path = Path.of("/.");

        final InputStream inputStream = ReadmeNamingParser.builder().templateLocation(templatePath).fileFilterChain(FileFilterChain.createDefaultChain()).build().buildReadmeStream(path);

        assertThat(inputStream).isNull();
    }

    @Test
    public void testBuildReadmeStreamNothing() throws URISyntaxException, IOException {
        final Path path = Path.of(getClass().getResource("/directory2NoReadme/noProject2").toURI());

        final InputStream inputStream = ReadmeNamingParser.builder().templateLocation(templatePath).fileFilterChain(FileFilterChain.createDefaultChain()).build().buildReadmeStream(path);

        assertThat(inputStream).isNull();
    }

    @Test
    public void testBuildReadmeStreamMixMavenAndNPMFlagOn() throws URISyntaxException, IOException {
        final Path path = Path.of(getClass().getResource("/directory2NoReadme/project3MavenAndNPM").toURI());

        final InputStream inputStream = ReadmeNamingParser.builder().isNoEmpty(true).templateLocation(templatePath).fileFilterChain(FileFilterChain.createDefaultChain()).build().buildReadmeStream(path);

        assertThat(inputStream).isNull();
    }

    @Test
    public void testBuildReadmeStreamMixMavenAndNPM() throws URISyntaxException, IOException {
        final Path path = Path.of(getClass().getResource("/directory2NoReadme/project3MavenAndNPM").toURI());

        final InputStream inputStream = ReadmeNamingParser.builder().templateLocation(templatePath).fileFilterChain(FileFilterChain.createDefaultChain()).build().buildReadmeStream(path);

        assertThat(inputStream).isNotNull();
        final String result = IOUtils.toString(inputStream, Charset.defaultCharset());
        assertThat(result).isEqualTo("# This is a test project");
    }

    @Test
    public void testBuildReadmeStreamMavenName() throws URISyntaxException, IOException {
        final Path path = Path.of(getClass().getResource("/directory2NoReadme/project1Maven").toURI());

        final InputStream inputStream = ReadmeNamingParser.builder().templateLocation(templatePath).fileFilterChain(FileFilterChain.createDefaultChain()).build().buildReadmeStream(path);

        assertThat(inputStream).isNotNull();
        final String result = IOUtils.toString(inputStream, Charset.defaultCharset());
        assertThat(result).isEqualTo("# This is a test project");
    }

    @Test
    public void testBuildReadmeStreamMavenNoName() throws URISyntaxException, IOException {

        final Path path = Path.of(getClass().getResource("/directory2NoReadme/project1MavenNoName").toURI());
        final InputStream inputStream = ReadmeNamingParser.builder().templateLocation(templatePath).fileFilterChain(FileFilterChain.createDefaultChain()).build().buildReadmeStream(path);

        assertThat(inputStream).isNotNull();
        final String result = IOUtils.toString(inputStream, Charset.defaultCharset());
        assertThat(result).isEqualTo("# ProjectMavenArtifact");
    }

    @Test
    public void testBuildReadmeStreamNPM() throws URISyntaxException, IOException {
        final Path path = Path.of(getClass().getResource("/directory2NoReadme/project2NPM").toURI());

        final InputStream inputStream = ReadmeNamingParser.builder().templateLocation(templatePath).fileFilterChain(FileFilterChain.createDefaultChain()).build().buildReadmeStream(path);

        assertThat(inputStream).isNotNull();
        final String result = IOUtils.toString(inputStream, Charset.defaultCharset());
        assertThat(result).isEqualTo("# npm-project");
    }

    @Test
    public void testBuildReadmeStreamGradle() throws URISyntaxException, IOException {
        final Path path = Path.of(getClass().getResource("/directory2NoReadme/project4Gradle").toURI());

        final InputStream inputStream = ReadmeNamingParser.builder().templateLocation(templatePath).fileFilterChain(FileFilterChain.createDefaultChain()).build().buildReadmeStream(path);

        assertThat(inputStream).isNotNull();
        final String result = IOUtils.toString(inputStream, Charset.defaultCharset());
        assertThat(result).isEqualTo("# project4Gradle");
    }

    @Test
    public void testBuildReadmeStreamSBT() throws URISyntaxException, IOException {
        final Path path = Path.of(getClass().getResource("/directory2NoReadme/project5Sbt").toURI());

        final InputStream inputStream = ReadmeNamingParser.builder().templateLocation(templatePath).fileFilterChain(FileFilterChain.createDefaultChain()).build().buildReadmeStream(path);

        assertThat(inputStream).isNotNull();
        final String result = IOUtils.toString(inputStream, Charset.defaultCharset());
        assertThat(result).isEqualTo("# sbt-project");
    }

}