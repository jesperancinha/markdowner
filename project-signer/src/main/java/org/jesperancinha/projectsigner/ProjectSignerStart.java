package org.jesperancinha.projectsigner;

import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.projectsigner.configuration.ProjectSignerOptions;
import org.jesperancinha.projectsigner.service.FinderService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;

import java.nio.file.Path;

@Slf4j
@SpringBootApplication
public class ProjectSignerStart implements ApplicationRunner {

    private final FinderService finderService;

    public static void main(String[] args) {
        SpringApplication.run(ProjectSignerStart.class);
    }

    public ProjectSignerStart(final FinderService finderService) {
        this.finderService = finderService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        final ProjectSignerOptions projectSignerOptions = new ProjectSignerOptions();
        new CommandLine(projectSignerOptions).parseArgs(args.getSourceArgs());

        finderService.iterateThroughFilesAndFolders(Path.of(projectSignerOptions.getRootDirectory()));
    }
}
