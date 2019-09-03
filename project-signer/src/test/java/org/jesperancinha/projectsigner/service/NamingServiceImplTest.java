package org.jesperancinha.projectsigner.service;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class NamingServiceImplTest {

    @MockBean
    private FinderServiceImpl finderService;

    @Autowired
    private NamingServiceImpl namingService;

    @Test
    void testBuildReadmeStreamMixMavenAndNPM() throws URISyntaxException, IOException {

        final Path path = Path.of(getClass().getResource("/directory2NoReadme/project3MavenAndNPM").toURI());
        final InputStream inputStream = namingService.buildReadmeStream(path);

        assertThat(inputStream).isNotNull();
        final String result = IOUtils.toString(inputStream, Charset.defaultCharset());
        assertThat(result).isEqualTo("# This is a test project");
    }

    @Test
    void testBuildReadmeStreamMavenName() throws URISyntaxException, IOException {

        final Path path = Path.of(getClass().getResource("/directory2NoReadme/project1Maven").toURI());
        final InputStream inputStream = namingService.buildReadmeStream(path);

        assertThat(inputStream).isNotNull();
        final String result = IOUtils.toString(inputStream, Charset.defaultCharset());
        assertThat(result).isEqualTo("# This is a test project");
    }

    @Test
    void testBuildReadmeStreamMavenNoName() throws URISyntaxException, IOException {

        final Path path = Path.of(getClass().getResource("/directory2NoReadme/project1MavenNoName").toURI());
        final InputStream inputStream = namingService.buildReadmeStream(path);

        assertThat(inputStream).isNotNull();
        final String result = IOUtils.toString(inputStream, Charset.defaultCharset());
        assertThat(result).isEqualTo("# ProjectMavenArtifact");
    }
    @Test
    void testBuildReadmeStreamNPM() throws URISyntaxException, IOException {

        final Path path = Path.of(getClass().getResource("/directory2NoReadme/project2NPM").toURI());
        final InputStream inputStream = namingService.buildReadmeStream(path);

        assertThat(inputStream).isNotNull();
        final String result = IOUtils.toString(inputStream, Charset.defaultCharset());
        assertThat(result).isEqualTo("# npm-project");
    }

    @Test
    void testBuildReadmeStreamGradle() throws URISyntaxException, IOException {

        final Path path = Path.of(getClass().getResource("/directory2NoReadme/project4Gradle").toURI());
        final InputStream inputStream = namingService.buildReadmeStream(path);

        assertThat(inputStream).isNotNull();
        final String result = IOUtils.toString(inputStream, Charset.defaultCharset());
        assertThat(result).isEqualTo("# project4Gradle");
    }

    @Test
    void testBuildReadmeStreamSBT() throws URISyntaxException, IOException {

        final Path path = Path.of(getClass().getResource("/directory2NoReadme/project5Sbt").toURI());
        final InputStream inputStream = namingService.buildReadmeStream(path);

        assertThat(inputStream).isNotNull();
        final String result = IOUtils.toString(inputStream, Charset.defaultCharset());
        assertThat(result).isEqualTo("# sbt-project");
    }
}