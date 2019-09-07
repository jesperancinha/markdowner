package org.jesperancinha.parser.markdowner.filter;

public abstract class ProjectFilter<P> {

    protected String lastProjectName;

    public abstract boolean test(P path);

    public abstract String lastProjectName();
}
