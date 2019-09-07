package org.jesperancinha.parser.markdowner.filter;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;

/**
 * Filter to check if folder contains a Gradle project and keeps the project name in memory
 */
@Slf4j
public class GradleFilter extends ProjectFilter<Path> {

    private static final String GRADLE_BUILD = "gradle.build";

    @Override
    public boolean test(Path path) {
        boolean isGradleBuild = path.getFileName().toString().equals(GRADLE_BUILD);
        if (isGradleBuild) {
            this.lastProjectName = path.getParent().getFileName().toString();
            return true;
        }
        return false;
    }

    @Override
    public String lastProjectName() {
        return lastProjectName;
    }
}
