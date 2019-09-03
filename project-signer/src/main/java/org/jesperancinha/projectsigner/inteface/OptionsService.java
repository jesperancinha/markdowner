package org.jesperancinha.projectsigner.inteface;

import org.jesperancinha.projectsigner.configuration.ProjectSignerOptions;

public interface OptionsService {
    ProjectSignerOptions processOptions(String[] args);

    ProjectSignerOptions getProjectSignerOptions();
}
