package org.jesperancinha.projectsigner.filter;

import java.nio.file.Path;

public class GradleFilter implements ProjectFilter<Path> {

    private static final String GRADLE_BUILD = "gradle.build";

    private String lastProjectName;

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
