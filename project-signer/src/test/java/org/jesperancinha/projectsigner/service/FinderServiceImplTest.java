package org.jesperancinha.projectsigner.service;

import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.projectsigner.configuration.ProjectSignerOptions;
import org.jesperancinha.projectsigner.inteface.FileWriterService;
import org.jesperancinha.projectsigner.inteface.MergeService;
import org.jesperancinha.projectsigner.inteface.OptionsService;
import org.jesperancinha.projectsigner.inteface.ReadmeNamingService;
import org.jesperancinha.projectsigner.inteface.ReadmeService;
import org.jesperancinha.projectsigner.inteface.TemplateService;
import org.jesperancinha.projectsigner.model.Paragraphs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class FinderServiceImplTest {

    private static final String MD_SUFFIX = ".md";

    private static final String README_PREFIX = "Readme";

    @InjectMocks
    private FinderServiceImpl finderService;

    @Mock
    private ReadmeNamingService readmeNamingService;

    @Mock
    private MergeService mergeService;

    @Mock
    private TemplateService templateService;

    @Mock
    private ReadmeService readmeService;

    @Mock
    private OptionsService optionsService;

    @Mock
    private FileWriterService fileWriterService;

    @TempDir
    public static Path tempDirectory;

    @Captor
    private ArgumentCaptor<InputStream> inputStreamArgumentCaptor;

    @Captor
    private ArgumentCaptor<Path> pathArgumentCaptor;

    @Test
    void testIterateThroughFilesAndFolders() throws IOException {
        final Path tempFile = Files.createTempFile(tempDirectory, README_PREFIX, MD_SUFFIX);
        final ProjectSignerOptions mockProjectSignerOptions = mock(ProjectSignerOptions.class);
        final Paragraphs mockParagraphs = mock(Paragraphs.class);
        when(mockProjectSignerOptions.getTemplateLocation()).thenReturn(tempFile);
        when(optionsService.getProjectSignerOptions()).thenReturn(mockProjectSignerOptions);
        when(templateService.findAllParagraphs(any(InputStream.class))).thenReturn(mockParagraphs);

        finderService.iterateThroughFilesAndFolders(tempDirectory);

        verify(optionsService).getProjectSignerOptions();
        verify(mockProjectSignerOptions).getTemplateLocation();
        verify(templateService).findAllParagraphs(inputStreamArgumentCaptor.capture());
        verify(readmeNamingService).buildReadmeStream(pathArgumentCaptor.capture());
        verifyZeroInteractions(fileWriterService);
        verifyZeroInteractions(readmeService);
        verifyZeroInteractions(mergeService);
        assertThat(pathArgumentCaptor.getValue().toString()).isEqualTo(tempDirectory.toString());
        assertThat(inputStreamArgumentCaptor.getValue()).isNotNull();
    }
}