package org.jesperancinha.projectsigner.service;

import org.jesperancinha.projectsigner.configuration.ProjectSignerOptions;
import org.jesperancinha.projectsigner.inteface.OptionsService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Service
@Profile({"test", "localtest", "default"})
public class OptionsServiceImplTest implements OptionsService {

    @Override
    public ProjectSignerOptions processOptions(final String[] args) {
        final ProjectSignerOptions projectSignerOptions = mock(ProjectSignerOptions.class);
        when(projectSignerOptions.getRootDirectory()).thenReturn(mock(Path.class));
        return projectSignerOptions;
    }
}
