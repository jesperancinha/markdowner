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

    private ProjectSignerOptions projectSignerOptions;

    @Override
    public ProjectSignerOptions processOptions(final String[] args) {
        final String rootPath = getClass().getResource("/").getPath();
        final String template = getClass().getResource("/Readme.md").getPath();
        final ProjectSignerOptions projectSignerOptions = mock(ProjectSignerOptions.class);
        when(projectSignerOptions.getRootDirectory()).thenReturn(Path.of(rootPath));
        when(projectSignerOptions.getTemplateLocation()).thenReturn(Path.of(template));
        when(projectSignerOptions.getTagNames()).thenReturn(new String[]{"License", "About me"});
        this.projectSignerOptions = projectSignerOptions;
        return projectSignerOptions;
    }

    @Override
    public ProjectSignerOptions getProjectSignerOptions() {
        return projectSignerOptions;
    }
}
