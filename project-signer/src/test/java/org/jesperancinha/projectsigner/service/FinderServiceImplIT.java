package org.jesperancinha.projectsigner.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Slf4j
class FinderServiceImplIT {

    @Autowired
    private FinderServiceImpl finderService;

    @TempDir
    public static Path tempDirectory;

    @BeforeEach
    public void setUp() throws IOException {
        final URL resource = getClass().getResource("/.");
        copyFolder(Path.of(resource.getPath()), tempDirectory);
    }

    @Test
    void testIterateThroughFilesAndFolders() throws IOException {
        finderService.iterateThroughFilesAndFolders(tempDirectory);

        final String subDirectory1 = getFileContent("directory1/subDirectory1/Readme.md");
        assertThat(subDirectory1).isEqualTo("# label1\n\n# label2\n\n# label3\n\n## License\nThis is one\nOne\n\n## About me\nThis is two\nTwo\n");
        final String directory1 = getFileContent("directory1/Readme.md");
        assertThat(directory1).isEqualTo("## label1\n\n### label2\n\n# label3\n\n## License\nThis is one\nOne\n\n## About me\nThis is two\nTwo\n");
        final String noProject2 = getFileContent("directory2NoReadme/noProject2/Readme.md");
        assertThat(noProject2).isNull();
        final String project1Maven = getFileContent("directory2NoReadme/project1Maven/Readme.md");
        assertThat(project1Maven).isEqualTo("# This is a test project\n\n## License\nThis is one\nOne\n\n## About me\nThis is two\nTwo\n");
        final String project1MavenNoName = getFileContent("directory2NoReadme/project1MavenNoName/Readme.md");
        assertThat(project1MavenNoName).isEqualTo("# ProjectMavenArtifact\n\n## License\nThis is one\nOne\n\n## About me\nThis is two\nTwo\n");
        final String project2NPM = getFileContent("directory2NoReadme/project2NPM/Readme.md");
        assertThat(project2NPM).isEqualTo("# npm-project\n\n## License\nThis is one\nOne\n\n## About me\nThis is two\nTwo\n");
        final String project3MavenAndNPM = getFileContent("directory2NoReadme/project3MavenAndNPM/Readme.md");
        assertThat(project3MavenAndNPM).isEqualTo("# This is a test project\n\n## License\nThis is one\nOne\n\n## About me\nThis is two\nTwo\n");
        final String project4Gradle = getFileContent("directory2NoReadme/project4Gradle/Readme.md");
        assertThat(project4Gradle).isEqualTo("# project4Gradle\n\n## License\nThis is one\nOne\n\n## About me\nThis is two\nTwo\n");
        final String project5Sbt = getFileContent("directory2NoReadme/project5Sbt/Readme.md");
        assertThat(project5Sbt).isEqualTo("# sbt-project\n\n## License\nThis is one\nOne\n\n## About me\nThis is two\nTwo\n");
    }

    private String getFileContent(String readmeLocation) throws IOException {
        File file = tempDirectory.resolve(readmeLocation).toFile();
        if (file.exists()) {
            return IOUtils.toString(new FileInputStream(file), Charset.defaultCharset());
        }
        return null;
    }

    private void copyFolder(Path src, Path dest) throws IOException {
        Files.walk(src)
                .forEach(source -> copy(source, dest.resolve(src.relativize(source))));
    }

    private void copy(Path src, Path dest) {
        try {
            FileUtils.deleteDirectory(dest.toFile());
            Files.copy(src, dest, REPLACE_EXISTING);
        } catch (Exception e) {
            log.error("Error found copying {} to {}", src, dest, e);
        }
    }
}