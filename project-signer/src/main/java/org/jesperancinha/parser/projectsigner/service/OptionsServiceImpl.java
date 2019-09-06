package org.jesperancinha.parser.projectsigner.service;

import org.jesperancinha.parser.markdowner.ReadmeNamingParser;
import org.jesperancinha.parser.projectsigner.configuration.ProjectSignerOptions;
import org.jesperancinha.parser.projectsigner.inteface.OptionsService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import picocli.CommandLine;

@Service
@Profile({"dev", "prod"})
public class OptionsServiceImpl extends OptionsService {

    @Override
    public ProjectSignerOptions processOptions(final String[] args) {
        final ProjectSignerOptions projectSignerOptions = new ProjectSignerOptions();
        new CommandLine(projectSignerOptions).parseArgs(args);
        this.projectSignerOptions = projectSignerOptions;
        commonBuilder = ReadmeNamingParser.builder()
                .templateLocation(this.projectSignerOptions.getTemplateLocation())
                .isNoEmpty(this.projectSignerOptions.isNoEmpty());
        return projectSignerOptions;
    }

}
