package org.jesperancinha.projectsigner.inteface;

import org.jesperancinha.parser.ReadmeNamingParser;
import org.jesperancinha.projectsigner.configuration.ProjectSignerOptions;

public abstract class OptionsService {

    protected ProjectSignerOptions projectSignerOptions;

    protected ReadmeNamingParser.ReadmeNamingParserBuilder commonBuilder;

    public abstract ProjectSignerOptions processOptions(String[] args);

    public ProjectSignerOptions getProjectSignerOptions() {
        return projectSignerOptions;
    }

    public ReadmeNamingParser.ReadmeNamingParserBuilder getCommonNamingParser() {
        return commonBuilder;
    }
}
