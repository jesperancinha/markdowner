package org.jesperancinha.projectsigner.service;

import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.projectsigner.inteface.FinderService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

import static java.nio.file.FileVisitOption.FOLLOW_LINKS;
import static java.nio.file.FileVisitResult.CONTINUE;

@Slf4j
@Service
public class FinderServiceImpl implements FinderService {

    @Override
    public void iterateThroughFilesAndFolders(Path rootPath) throws IOException {
        final EnumSet<FileVisitOption> opts = EnumSet.of(FOLLOW_LINKS);
        Files.walkFileTree(rootPath, opts, Integer.MAX_VALUE, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                log.trace("Visiting file {}", file);
                return CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException e) {
                if (ObjectUtils.isEmpty(e)) {
                    log.trace("Visiting path {}", dir);
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
