package org.jesperancinha.projectsigner.filter;

import java.io.File;
import java.nio.file.Path;

public class GradleFilter implements ProjectFilter<File> {
    @Override
    public boolean test(Path path) {
        return path.getFileName().toString().equals("gradle.build");
    }
}
