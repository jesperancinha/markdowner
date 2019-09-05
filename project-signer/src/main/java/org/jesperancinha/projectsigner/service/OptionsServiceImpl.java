package org.jesperancinha.projectsigner.service;

import org.jesperancinha.projectsigner.configuration.ProjectSignerOptions;
import org.jesperancinha.projectsigner.inteface.OptionsService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

import java.nio.file.Path;

@Service
@Profile({"dev", "prod"})
public class OptionsServiceImpl implements OptionsService {

    private ProjectSignerOptions projectSignerOptions;

    @Override
    public ProjectSignerOptions processOptions(final String[] args) {
        final ProjectSignerOptions projectSignerOptions = new ProjectSignerOptions();
        new CommandLine(projectSignerOptions).parseArgs(args);
        this.projectSignerOptions = projectSignerOptions;
        return projectSignerOptions;
    }

    @Override
    public ProjectSignerOptions getProjectSignerOptions() {
        return projectSignerOptions;
    }

    @Override
    public Path getTemplateLocation() {
        return this.projectSignerOptions.getTemplateLocation();
    }

    @Override
    public boolean isNoEmpty() {
        return this.projectSignerOptions.isNoEmpty();
    }
}
