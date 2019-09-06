package org.jesperancinha.parser.markdowner.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PackageInfo {
    private final ProjectType projectType;
    private final String projectName;
}
