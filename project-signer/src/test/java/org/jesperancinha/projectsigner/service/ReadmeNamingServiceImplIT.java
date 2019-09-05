package org.jesperancinha.projectsigner.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
class ReadmeNamingServiceImplIT {

    @MockBean
    private FinderServiceImpl finderService;

    @Autowired
    private ReadmeNamingServiceImpl namingService;

    @Autowired
    private OptionsServiceMock optionsService;

    @BeforeEach
    public void setUp() {
        optionsService.setNoEmptyDown();
        log.info(optionsService.getProjectSignerOptions().getRootDirectory().toString());
    }

    @Test
    public void testBuildReadmeSamePath() throws IOException {
        final Path path = Path.of(optionsService.getProjectSignerOptions().getTemplateLocation().toString());

        final InputStream inputStream = namingService.buildReadmeStream(path);

        assertThat(inputStream).isNull();
    }

    @Test
    public void testBuildReadmeStreamNothing() throws URISyntaxException, IOException {
        final Path path = Path.of(getClass().getResource("/directory2NoReadme/noProject2").toURI());

        final InputStream inputStream = namingService.buildReadmeStream(path);

        assertThat(inputStream).isNull();
    }

    @Test
    public void testBuildReadmeStreamMixMavenAndNPMFlagOn() throws URISyntaxException, IOException {
        optionsService.setNoEmptyUp();
        final Path path = Path.of(getClass().getResource("/directory2NoReadme/project3MavenAndNPM").toURI());

        final InputStream inputStream = namingService.buildReadmeStream(path);

        assertThat(inputStream).isNull();
    }

    @Test
    public void testBuildReadmeStreamMixMavenAndNPM() throws URISyntaxException, IOException {
        final Path path = Path.of(getClass().getResource("/directory2NoReadme/project3MavenAndNPM").toURI());

        final InputStream inputStream = namingService.buildReadmeStream(path);

        assertThat(inputStream).isNotNull();
        final String result = IOUtils.toString(inputStream, Charset.defaultCharset());
        assertThat(result).isEqualTo("# This is a test project");
    }

    @Test
    public void testBuildReadmeStreamMavenName() throws URISyntaxException, IOException {
        final Path path = Path.of(getClass().getResource("/directory2NoReadme/project1Maven").toURI());

        final InputStream inputStream = namingService.buildReadmeStream(path);

        assertThat(inputStream).isNotNull();
        final String result = IOUtils.toString(inputStream, Charset.defaultCharset());
        assertThat(result).isEqualTo("# This is a test project");
    }

    @Test
    public void testBuildReadmeStreamMavenNoName() throws URISyntaxException, IOException {

        final Path path = Path.of(getClass().getResource("/directory2NoReadme/project1MavenNoName").toURI());
        final InputStream inputStream = namingService.buildReadmeStream(path);

        assertThat(inputStream).isNotNull();
        final String result = IOUtils.toString(inputStream, Charset.defaultCharset());
        assertThat(result).isEqualTo("# ProjectMavenArtifact");
    }

    @Test
    public void testBuildReadmeStreamNPM() throws URISyntaxException, IOException {
        final Path path = Path.of(getClass().getResource("/directory2NoReadme/project2NPM").toURI());

        final InputStream inputStream = namingService.buildReadmeStream(path);

        assertThat(inputStream).isNotNull();
        final String result = IOUtils.toString(inputStream, Charset.defaultCharset());
        assertThat(result).isEqualTo("# npm-project");
    }

    @Test
    public void testBuildReadmeStreamGradle() throws URISyntaxException, IOException {
        final Path path = Path.of(getClass().getResource("/directory2NoReadme/project4Gradle").toURI());

        final InputStream inputStream = namingService.buildReadmeStream(path);

        assertThat(inputStream).isNotNull();
        final String result = IOUtils.toString(inputStream, Charset.defaultCharset());
        assertThat(result).isEqualTo("# project4Gradle");
    }

    @Test
    public void testBuildReadmeStreamSBT() throws URISyntaxException, IOException {
        final Path path = Path.of(getClass().getResource("/directory2NoReadme/project5Sbt").toURI());

        final InputStream inputStream = namingService.buildReadmeStream(path);

        assertThat(inputStream).isNotNull();
        final String result = IOUtils.toString(inputStream, Charset.defaultCharset());
        assertThat(result).isEqualTo("# sbt-project");
    }

    @AfterEach
    public void tearDown() throws IOException {
        verify(finderService, atLeast(0)).iterateThroughFilesAndFolders(optionsService.getProjectSignerOptions().getRootDirectory());
        verifyNoMoreInteractions(finderService);
    }
}