package org.jesperancinha.projectsigner.configuration;

import org.jesperancinha.projectsigner.filter.FileFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectSignerConfiguration {

    @Bean
    public FileFilterChain fileFilterChain(){
        return new FileFilterChain();
    }
}
