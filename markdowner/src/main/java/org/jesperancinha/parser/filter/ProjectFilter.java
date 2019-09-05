package org.jesperancinha.parser.filter;

public interface ProjectFilter<P> {
    boolean test(P path);

    String lastProjectName();
}
