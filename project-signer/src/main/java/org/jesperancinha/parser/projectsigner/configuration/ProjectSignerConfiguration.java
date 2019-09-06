package org.jesperancinha.parser.projectsigner.configuration;

import org.jesperancinha.parser.markdowner.filter.FileFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.jesperancinha.parser.markdowner.filter.FileFilterChain.createDefaultChain;

@Configuration
public class ProjectSignerConfiguration {

    @Bean
    public FileFilterChain fileFilterChain() {
        return createDefaultChain();
    }
}
