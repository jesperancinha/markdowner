package org.jesperancinha.projectsigner.filter;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.parser.model.PackageInfo;
import org.jesperancinha.parser.model.ProjectType;

import java.nio.file.Path;
import java.util.Objects;

@Slf4j
@Builder
public class FileFilterChain {

    private final FileFilterChain nextFileFilterChain;

    private final ProjectFilter<Path> projectFilter;

    private final ProjectType projectType;

    public PackageInfo findHighest(PackageInfo packageInfo, Path path) {

        if (Objects.nonNull(packageInfo) && packageInfo.getProjectType() == projectType) {
            return packageInfo;
        }

        if (projectFilter.test(path)) {
            return PackageInfo.builder().projectName(projectFilter.lastProjectName()).projectType(projectType).build();
        }

        if (Objects.nonNull(nextFileFilterChain)) {
            return nextFileFilterChain.findHighest(packageInfo, path);
        }
        return packageInfo;
    }
}
