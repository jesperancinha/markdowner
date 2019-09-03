package org.jesperancinha.projectsigner.filter;

import java.nio.file.Path;

public interface ProjectFilter<P> {
    boolean test(Path path);
}
