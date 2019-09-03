package org.jesperancinha.projectsigner.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.projectsigner.model.PackageInfo;
import org.jesperancinha.projectsigner.model.ProjectType;

import java.nio.file.Path;

@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileFilterChain {

    private FileFilterChain nextFileFilterChain;

    private ProjectFilter<Path> projectFilter;

    private ProjectType projectType;

    public PackageInfo findHighest(PackageInfo packageInfo, Path path) {

        if (packageInfo.getProjectType() == projectType) {
            return packageInfo;
        }

        if (projectFilter.test(path)) {
//            return PackageInfo.builder().projectName()
        }
        return null;
    }
}
