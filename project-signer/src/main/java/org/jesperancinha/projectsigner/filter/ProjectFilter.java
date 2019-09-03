package org.jesperancinha.projectsigner.filter;

public interface ProjectFilter<P> {
    boolean test(P path);

    String lastProjectName();
}
