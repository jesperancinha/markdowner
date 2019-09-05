package org.jesperancinha.projectsigner.filter;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.projectsigner.inteface.FileWriterService;
import org.jesperancinha.projectsigner.inteface.MergeService;
import org.jesperancinha.projectsigner.inteface.OptionsService;
import org.jesperancinha.projectsigner.inteface.ReadmeNamingService;
import org.jesperancinha.projectsigner.inteface.ReadmeService;
import org.jesperancinha.projectsigner.model.Paragraphs;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.SKIP_SUBTREE;

@Slf4j
@Builder
public class ProjectSignerVisitor extends SimpleFileVisitor<Path> {

    private ReadmeNamingService readmeNamingService;
    private MergeService mergeService;
    private ReadmeService readmeService;
    private OptionsService optionsService;
    private FileWriterService fileWriterService;
    private Paragraphs allParagraphs;

    public ProjectSignerVisitor(ReadmeNamingService readmeNamingService,
                                MergeService mergeService,
                                ReadmeService readmeService,
                                OptionsService optionsService,
                                FileWriterService fileWriterService, Paragraphs allParagraphs) {
        this.readmeNamingService = readmeNamingService;
        this.mergeService = mergeService;
        this.readmeService = readmeService;
        this.optionsService = optionsService;
        this.fileWriterService = fileWriterService;
        this.allParagraphs = allParagraphs;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        log.trace("Visiting file {}", file);
        return CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        if (isIgnorableFolder(dir)) {
            return SKIP_SUBTREE;
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
        if (isIgnorableFolder(dir)) {
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

    private boolean isIgnorableFolder(Path dir) {
        return dir.getFileName().toString().equalsIgnoreCase("resources")
                ||
                dir.getFileName().toString().equalsIgnoreCase("project-signer-templates");
    }

}
