package org.jesperancinha.parser.markdowner.filter;

public interface ProjectFilter<P> {
    boolean test(P path);

    String lastProjectName();
}
