package org.jesperancinha.projectsigner.filter;

import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;

public class GradleFilter implements ProjectFilter<Path> {

    private String lastProjectName;

    @Override
    public boolean test(Path path) {
        this.lastProjectName = path.getParent().getFileName().toString();
        return path.getFileName().toString().equals("gradle.build");
    }

    @Override
    public String lastProjectName() {
        return lastProjectName;
    }
}
