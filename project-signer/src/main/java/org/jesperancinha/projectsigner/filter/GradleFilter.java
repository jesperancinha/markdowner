package org.jesperancinha.projectsigner.filter;

import java.nio.file.Path;

public class GradleFilter implements ProjectFilter<Path> {

    private static final String GRADLE_BUILD = "gradle.build";

    private String lastProjectName;

    @Override
    public boolean test(Path path) {
        this.lastProjectName = path.getParent().getFileName().toString();
        return path.getFileName().toString().equals(GRADLE_BUILD);
    }

    @Override
    public String lastProjectName() {
        return lastProjectName;
    }
}
