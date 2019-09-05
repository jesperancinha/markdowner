package org.jesperancinha.projectsigner.service;

import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.projectsigner.filter.ProjectSignerVisitor;
import org.jesperancinha.projectsigner.inteface.FileWriterService;
import org.jesperancinha.projectsigner.inteface.FinderService;
import org.jesperancinha.projectsigner.inteface.MergeService;
import org.jesperancinha.projectsigner.inteface.OptionsService;
import org.jesperancinha.projectsigner.inteface.ReadmeNamingService;
import org.jesperancinha.projectsigner.inteface.ReadmeService;
import org.jesperancinha.projectsigner.inteface.TemplateService;
import org.jesperancinha.projectsigner.model.Paragraphs;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;

import static java.lang.Integer.MAX_VALUE;
import static java.nio.file.FileVisitOption.FOLLOW_LINKS;

@Slf4j
@Service
public class FinderServiceImpl implements FinderService {

    private ReadmeNamingService readmeNamingService;
    private MergeService mergeService;
    private TemplateService templateService;
    private ReadmeService readmeService;
    private OptionsService optionsService;
    private FileWriterService fileWriterService;

    public FinderServiceImpl(
            final ReadmeNamingService readmeNamingService,
            final MergeService mergeService,
            final TemplateService templateService,
            final ReadmeService readmeService,
            final OptionsService optionsService,
            final FileWriterService fileWriterService
    ) {

        this.readmeNamingService = readmeNamingService;
        this.mergeService = mergeService;
        this.templateService = templateService;
        this.readmeService = readmeService;
        this.optionsService = optionsService;
        this.fileWriterService = fileWriterService;
    }

    @Override
    public void iterateThroughFilesAndFolders(Path rootPath) throws IOException {

        final File fileTemplate = optionsService.getProjectSignerOptions().getTemplateLocation().toFile();
        final FileInputStream templateInputStream = new FileInputStream(fileTemplate);
        final Paragraphs allParagraphs = templateService.findAllParagraphs(templateInputStream);

        final EnumSet<FileVisitOption> opts = EnumSet.of(FOLLOW_LINKS);
        Files.walkFileTree(rootPath, opts, MAX_VALUE, new ProjectSignerVisitor(
                readmeNamingService,
                mergeService,
                readmeService,
                optionsService,
                fileWriterService,
                allParagraphs)
        );
    }

}
