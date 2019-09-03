package org.jesperancinha.projectsigner.configuration;

import org.jesperancinha.projectsigner.filter.*;
import org.jesperancinha.projectsigner.model.ProjectType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectSignerConfiguration {

    @Bean
    public FileFilterChain fileFilterChain() {
        return FileFilterChain.builder().projectType(ProjectType.MAVEN).projectFilter(new MavenFilter())
                .nextFileFilterChain(
                        createNPMChain()
                ).build();
    }

    private FileFilterChain createNPMChain() {
        return FileFilterChain.builder().projectType(ProjectType.NPM).projectFilter(new NPMFilter())
                .nextFileFilterChain(
                        createGradleChain()
                ).build();
    }

    private FileFilterChain createGradleChain() {
        return FileFilterChain.builder().projectType(ProjectType.GRADLE).projectFilter(new GradleFilter())
                .nextFileFilterChain(
                        createSBTChain()
                ).build();
    }

    private FileFilterChain createSBTChain() {
        return FileFilterChain.builder().projectType(ProjectType.SBT).projectFilter(new SBTFilter())
                .nextFileFilterChain(null)
                .build();
    }
}
