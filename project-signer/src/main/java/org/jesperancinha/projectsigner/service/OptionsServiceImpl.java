package org.jesperancinha.projectsigner.service;

import org.jesperancinha.projectsigner.configuration.ProjectSignerOptions;
import org.jesperancinha.projectsigner.inteface.OptionsService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

@Service
@Profile({"dev", "prod"})
public class OptionsServiceImpl implements OptionsService {

    @Override
    public ProjectSignerOptions processOptions(final String[] args) {
        final ProjectSignerOptions projectSignerOptions = new ProjectSignerOptions();
        new CommandLine(projectSignerOptions).parseArgs(args);
        return projectSignerOptions;
    }
}
