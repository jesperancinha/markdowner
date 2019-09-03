package org.jesperancinha.projectsigner.filter;

import java.util.function.Predicate;

public interface ProjectFilter<P>{
    boolean test(P path);

    String lastProjectName();
}
