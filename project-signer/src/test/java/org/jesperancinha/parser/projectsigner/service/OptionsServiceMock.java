package org.jesperancinha.parser.projectsigner.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.jesperancinha.parser.markdowner.ReadmeNamingParser;
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptions;
import org.jesperancinha.parser.projectsigner.inteface.OptionsService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
@Profile({"test", "localtest", "default"})
public class OptionsServiceMock extends OptionsService {
    public void setNoEmptyUp() {
        Mockito.clearInvocations(projectSignerOptions);
        when(projectSignerOptions.isNoEmpty()).thenReturn(true);
        commonBuilder = ReadmeNamingParser.builder()
            .templateLocation(this.projectSignerOptions.getTemplateLocation())
            .isNoEmpty(this.projectSignerOptions.isNoEmpty());
    }

    @Override
    public ProjectSignerOptions processOptions(final String[] args) {
        final String rootPath = getClass().getResource("/dummyDirectory").getPath();
        final String template = getClass().getResource("/Readme.md").getPath();
        final ProjectSignerOptions projectSignerOptions = mock(ProjectSignerOptions.class);
        when(projectSignerOptions.getRootDirectory()).thenReturn(Path.of(rootPath));
        when(projectSignerOptions.getTemplateLocation()).thenReturn(Path.of(template));
        when(projectSignerOptions.getTagNames()).thenReturn(new String[]{"License", "About me"});
        this.projectSignerOptions = projectSignerOptions;
        commonBuilder = ReadmeNamingParser.builder()
            .templateLocation(this.projectSignerOptions.getTemplateLocation())
            .isNoEmpty(this.projectSignerOptions.isNoEmpty());
        return projectSignerOptions;
    }

    public void setNoEmptyDown() {
        Mockito.clearInvocations(projectSignerOptions);
        when(projectSignerOptions.isNoEmpty()).thenReturn(false);
        commonBuilder = ReadmeNamingParser.builder()
            .templateLocation(this.projectSignerOptions.getTemplateLocation())
            .isNoEmpty(this.projectSignerOptions.isNoEmpty());
    }
}
