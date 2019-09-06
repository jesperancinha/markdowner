package org.jesperancinha.projectsigner.service;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FileWriterServiceImplTest {

    private static final String README_SIGNED_FILE = "Readme signed file";

    @InjectMocks
    private FileWriterServiceImpl fileWriterService;

    @TempDir
    public static Path tempDirectory;

    @Test
    public void testExportReadmeFile() throws IOException {
        fileWriterService.exportReadmeFile(tempDirectory, README_SIGNED_FILE);

        final Path resultPath = tempDirectory.resolve("Readme.md");
        final File resultFile = resultPath.toFile();
        final InputStreamReader fileReader = new InputStreamReader(new FileInputStream(resultFile));
        final String result = IOUtils.toString(fileReader);
        assertThat(result).isEqualTo(README_SIGNED_FILE);
    }
}