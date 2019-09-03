package org.jesperancinha.projectsigner;

import org.jesperancinha.projectsigner.service.FinderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProjectSignerStartTest {

    @MockBean
    private FinderServiceImpl finderService;

    @Captor
    private ArgumentCaptor<Path> pathArgumentCaptor;

    @Test
    void run() throws IOException {
        verify(finderService).iterateThroughFilesAndFolders(pathArgumentCaptor.capture());
        assertThat(pathArgumentCaptor.getValue()).isNotNull();
    }
}