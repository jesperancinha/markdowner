package org.jesperancinha.projectsigner;

import org.jesperancinha.projectsigner.service.FinderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProjectSignerStartIT {

    @MockBean
    private FinderServiceImpl finderService;

    @Captor
    private ArgumentCaptor<Path> pathArgumentCaptor;

    @Test
    void run() throws IOException {
        verify(finderService, atLeast(0)).iterateThroughFilesAndFolders(pathArgumentCaptor.capture());
        verifyNoMoreInteractions(finderService);
        if (!CollectionUtils.isEmpty(pathArgumentCaptor.getAllValues())) {
            assertThat(pathArgumentCaptor.getValue()).isNotNull();
        }
    }
}