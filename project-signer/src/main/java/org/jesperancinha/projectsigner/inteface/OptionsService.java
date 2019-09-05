package org.jesperancinha.projectsigner.inteface;

import org.jesperancinha.projectsigner.configuration.ProjectSignerOptions;

import java.nio.file.Path;

public interface OptionsService {
    ProjectSignerOptions processOptions(String[] args);

    ProjectSignerOptions getProjectSignerOptions();

    Path getTemplateLocation();

    boolean isNoEmpty();
}
