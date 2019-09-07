package org.jesperancinha.parser.markdowner.model;

import lombok.Builder;
import lombok.Getter;
import org.jesperancinha.parser.markdowner.filter.FileFilterChain;

@Builder
@Getter
public class PackageInfo {
    private final FileFilterChain fileFilterChain;
    private final String projectName;
}
