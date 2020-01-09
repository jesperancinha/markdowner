package org.jesperancinha.parser.markdowner.filter;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.util.List;

/**
 * Filter to check if folder contains a Gradle project and keeps the project name in memory
 */
@Slf4j
public class GradleFilter extends ProjectFilter<Path> {

    private static final List<String> gradleBuilds = List.of(
            "gradle.build", "build.gradle");

    @Override
    public boolean test(Path path) {
        boolean isGradleBuild = gradleBuilds.contains(path.getFileName().toString());
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
