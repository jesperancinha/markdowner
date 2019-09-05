package org.jesperancinha.projectsigner.configuration;

import org.jesperancinha.projectsigner.filter.FileFilterChain;
import org.jesperancinha.projectsigner.filter.GradleFilter;
import org.jesperancinha.projectsigner.filter.MavenFilter;
import org.jesperancinha.projectsigner.filter.NPMFilter;
import org.jesperancinha.projectsigner.filter.SBTFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.jesperancinha.parser.model.ProjectType.GRADLE;
import static org.jesperancinha.parser.model.ProjectType.MAVEN;
import static org.jesperancinha.parser.model.ProjectType.NPM;
import static org.jesperancinha.parser.model.ProjectType.SBT;

@Configuration
public class ProjectSignerConfiguration {

    @Bean
    public FileFilterChain fileFilterChain() {
        return FileFilterChain.builder().projectType(MAVEN).projectFilter(new MavenFilter())
                .nextFileFilterChain(
                        createNPMChain()
                ).build();
    }

    private FileFilterChain createNPMChain() {
        return FileFilterChain.builder().projectType(NPM).projectFilter(new NPMFilter())
                .nextFileFilterChain(
                        createGradleChain()
                ).build();
    }

    private FileFilterChain createGradleChain() {
        return FileFilterChain.builder().projectType(GRADLE).projectFilter(new GradleFilter())
                .nextFileFilterChain(
                        createSBTChain()
                ).build();
    }

    private FileFilterChain createSBTChain() {
        return FileFilterChain.builder().projectType(SBT).projectFilter(new SBTFilter())
                .nextFileFilterChain(null)
                .build();
    }
}
