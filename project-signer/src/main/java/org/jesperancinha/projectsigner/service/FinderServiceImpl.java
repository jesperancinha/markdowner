package org.jesperancinha.projectsigner.service;

import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.projectsigner.inteface.FileWriterService;
import org.jesperancinha.projectsigner.inteface.FinderService;
import org.jesperancinha.projectsigner.inteface.MergeService;
import org.jesperancinha.projectsigner.inteface.OptionsService;
import org.jesperancinha.projectsigner.inteface.ReadmeNamingService;
import org.jesperancinha.projectsigner.inteface.ReadmeService;
import org.jesperancinha.projectsigner.inteface.TemplateService;
import org.jesperancinha.projectsigner.model.Paragraphs;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.Objects;

import static java.nio.file.FileVisitOption.FOLLOW_LINKS;
import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.SKIP_SUBTREE;

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
        Files.walkFileTree(rootPath, opts, Integer.MAX_VALUE, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                log.trace("Visiting file {}", file);
                return CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                if (dir.getFileName().toString().equalsIgnoreCase("resources")) {
                    return SKIP_SUBTREE;
                }
                return CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
                if (dir.getFileName().toString().equalsIgnoreCase("resources")) {
                    return SKIP_SUBTREE;
                }
                if (ObjectUtils.isEmpty(e)) {
                    final InputStream inputStream = readmeNamingService.buildReadmeStream(dir);
                    if (Objects.nonNull(inputStream)) {
                        log.trace("Visiting path {}", dir);
                        final String readme = readmeService.readDataSprippedOfTags(inputStream, optionsService.getProjectSignerOptions().getTagNames());
                        final String newText = mergeService.mergeDocumentWithFooterTemplate(readme, allParagraphs);
                        log.trace("New readme:\n {}", newText);
                        fileWriterService.exportReadmeFile(dir, newText);
                    }
                } else {
                    log.error("Failed on file {}", dir, e);
                }
                return CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file,
                                                   IOException exc) {
                if (exc instanceof AccessDeniedException) {
                    log.warn("Access denied on file {}", file);
                } else {
                    log.error("Error on file {}", file, exc);
                }
                return CONTINUE;
            }
        });
    }
}
