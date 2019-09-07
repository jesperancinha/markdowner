package org.jesperancinha.parser.markdowner.filter;

import static org.jesperancinha.parser.markdowner.model.ProjectType.GRADLE;
import static org.jesperancinha.parser.markdowner.model.ProjectType.MAVEN;
import static org.jesperancinha.parser.markdowner.model.ProjectType.NPM;
import static org.jesperancinha.parser.markdowner.model.ProjectType.SBT;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.jesperancinha.parser.markdowner.model.PackageInfo;
import org.jesperancinha.parser.markdowner.model.ProjectType;

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

    public static FileFilterChain createDefaultChain() {
        return FileFilterChain.builder().projectType(MAVEN).projectFilter(new MavenFilter())
            .nextFileFilterChain(
                createNPMChain()
            ).build();
    }

    private static FileFilterChain createNPMChain() {
        return FileFilterChain.builder().projectType(NPM).projectFilter(new NPMFilter())
            .nextFileFilterChain(
                createGradleChain()
            ).build();
    }

    private static FileFilterChain createGradleChain() {
        return FileFilterChain.builder().projectType(GRADLE).projectFilter(new GradleFilter())
            .nextFileFilterChain(
                createSBTChain()
            ).build();
    }

    private static FileFilterChain createSBTChain() {
        return FileFilterChain.builder().projectType(SBT).projectFilter(new SBTFilter())
            .nextFileFilterChain(null)
            .build();
    }
}
