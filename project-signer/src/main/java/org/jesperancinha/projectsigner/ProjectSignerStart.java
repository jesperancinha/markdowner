package org.jesperancinha.projectsigner;

import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.projectsigner.configuration.ProjectSignerOptions;
import org.jesperancinha.projectsigner.inteface.OptionsService;
import org.jesperancinha.projectsigner.service.FinderServiceImpl;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ProjectSignerStart implements ApplicationRunner {


    private final FinderServiceImpl finderServiceImpl;
    private final OptionsService optionsService;

    public static void main(String[] args) {
        SpringApplication.run(ProjectSignerStart.class, args);
    }

    public ProjectSignerStart(final FinderServiceImpl finderServiceImpl, final OptionsService optionsService) {
        this.finderServiceImpl = finderServiceImpl;
        this.optionsService = optionsService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ProjectSignerOptions projectSignerOptions = optionsService.processOptions(args.getSourceArgs());
        finderServiceImpl.iterateThroughFilesAndFolders(projectSignerOptions.getRootDirectory());
    }
}
